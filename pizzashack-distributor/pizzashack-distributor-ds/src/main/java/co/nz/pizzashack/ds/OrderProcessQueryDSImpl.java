package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustEmail;
import static co.nz.pizzashack.data.predicates.OrderProcessPredicates.findByCustomerEmail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.OrderProcessConverter;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.model.CustomerModel;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.repository.CustomerRepository;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.wf.ActivitiFacade;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class OrderProcessQueryDSImpl implements OrderProcessQueryDS {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessQueryDSImpl.class);

	@Resource
	private ActivitiFacade activitiFacade;

	@Resource
	private OrderProcessRepository orderProcessRepository;

	@Resource
	private OrderProcessConverter orderProcessConverter;

	@Resource
	private OrderProcessAccessor orderProcessAccessor;

	@Resource
	private CustomerRepository customerRepository;

	@Override
	public OrderProcessDto getOrderProcessByOrderNo(String orderNo)
			throws Exception {
		LOGGER.info("getOrderProcessByOrderNo start:{} ", orderNo);
		OrderProcessDto found = null;
		OrderProcessModel orderProcessModel = orderProcessAccessor
				.getOrderProcessByOrderNo(orderNo);
		found = orderProcessConverter.toDto(orderProcessModel);
		this.buildPendingActivity(found);
		LOGGER.info("getOrderProcessByOrderNo end:{} ", found);
		return found;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<OrderProcessDto> getOrderProcessesByCustomer(String customerEmail)
			throws Exception {
		LOGGER.info("getOrderProcessesByCustomer start:{} ", customerEmail);
		Set<OrderProcessDto> orderProcesses = null;
		CustomerModel customerModel = customerRepository
				.findOne(findByCustEmail(customerEmail));

		if (customerModel == null) {
			throw new NotFoundException("Customer not found by email["
					+ customerEmail + "]");
		}

		Iterable<OrderProcessModel> resultIterable = orderProcessRepository
				.findAll(findByCustomerEmail(customerEmail));

		List<OrderProcessModel> resultList = IteratorUtils
				.toList(resultIterable.iterator());
		if (resultList != null && resultList.size() > 0) {
			orderProcesses = new HashSet<OrderProcessDto>();
			for (OrderProcessModel orderProcessModel : resultList) {
				orderProcesses.add(orderProcessConverter
						.toDto(orderProcessModel));
			}
		}

		return orderProcesses;
	}

	@Override
	public OrderProcessDto getOrderProcessDtoById(Long orderProcessId)
			throws Exception {
		LOGGER.info("getOrderProcessDtoById start:{} ", orderProcessId);
		OrderProcessDto found = null;
		OrderProcessModel foundModel = orderProcessRepository
				.findOne(orderProcessId);
		if (foundModel == null) {
			throw new NotFoundException("OrderProcess not found by id["
					+ orderProcessId + "]");
		}
		found = orderProcessConverter.toDto(foundModel);
		this.buildPendingActivity(found);
		LOGGER.info("getOrderProcessDtoById end:{} ", found);
		return found;
	}

	private void buildPendingActivity(OrderProcessDto orderProcessDto) {
		LOGGER.info("buildPendingActivity start:{}");
		String orderNo = orderProcessDto.getOrder().getOrderNo();
		String processDefinitionId = orderProcessDto
				.getActiveProcessDefinitionId();
		ProcessActivityDto pendingActivity = activitiFacade
				.getExecutionActivityBasicInfo(orderNo, processDefinitionId,
						orderProcessDto.getActiveProcesssInstanceId(), false,
						true);

		if (pendingActivity.getType().equals("userTask")) {
			Task pendingTask = orderProcessAccessor.getPendingTask(
					pendingActivity.getName(), orderNo);
			orderProcessAccessor.buildTaskDetails(pendingTask, pendingActivity,
					processDefinitionId);
		}
		orderProcessDto.setPendingActivity(pendingActivity);
		LOGGER.info("buildPendingActivity end:{}");
	}

	@Override
	public Set<ProcessActivityDto> getHistoryProcessActivitiesByOrderNo(
			String orderNo) throws Exception {

		return null;
	}

}
