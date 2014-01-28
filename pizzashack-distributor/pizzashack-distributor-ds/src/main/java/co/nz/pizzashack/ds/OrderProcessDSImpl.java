package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.OrderProcessPredicates.findByOrderNo;
import static co.nz.pizzashack.data.predicates.WorkflowPredicates.findByProcessDefinitionKeyAndCategory;
import static co.nz.pizzashack.data.predicates.PizzashackPredicates.findByPizzashackName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.CustomerConverter;
import co.nz.pizzashack.data.converter.OrderConverter;
import co.nz.pizzashack.data.converter.OrderProcessConverter;
import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.dto.OrderDetailsDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.CustomerModel;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderModel.OrderStatus;
import co.nz.pizzashack.data.model.OrderPizzashackModel;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.model.PizzashackModel;
import co.nz.pizzashack.data.model.UserModel;
import co.nz.pizzashack.data.model.WorkflowModel;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.data.repository.PizzashackRepository;
import co.nz.pizzashack.data.repository.UserRepository;
import co.nz.pizzashack.data.repository.WorkflowRepository;
import co.nz.pizzashack.ds.OrderProcessAccessor.PendingActivityBuildOperation;
import co.nz.pizzashack.wf.ActivitiFacade;
import static co.nz.pizzashack.DistributorConstants.BILLING_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class OrderProcessDSImpl implements OrderProcessDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessDSImpl.class);

	@Resource
	private ActivitiFacade activitiFacade;

	@Resource
	private UserConverter userConverter;

	@Resource
	private TaskService taskService;

	@Resource
	private WorkflowRepository workflowRepository;

	@Resource
	private PizzashackRepository pizzashackRepository;

	@Resource
	private OrderProcessRepository orderProcessRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private OrderProcessConverter orderProcessConverter;

	@Resource
	private OrderConverter orderConverter;

	@Resource
	private CustomerConverter customerConverter;

	@Resource
	private OrderProcessAccessor orderProcessAccessor;

	public static final String PROCESS_DEFINITION_KEY = "orderlocalProcess";
	public static final String CATEGORY = "order";

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto startOrderProcess(UserDto operator) throws Exception {
		LOGGER.info("startOrderProcess start:{}", operator);
		OrderProcessDto orderProcessDto = null;
		OrderProcessModel orderProcessModel = null;
		OrderModel orderModel = null;

		WorkflowModel foundWorkflowModel = workflowRepository
				.findOne(findByProcessDefinitionKeyAndCategory(
						PROCESS_DEFINITION_KEY, CATEGORY));
		if (foundWorkflowModel == null) {
			throw new NotFoundException(
					"Process definition not found by processDefinitionKey["
							+ PROCESS_DEFINITION_KEY + "]");
		}

		// built PublishTrans object for process and persist it
		String processDefinitionId = foundWorkflowModel
				.getProcessDefinitionId();

		orderProcessModel = new OrderProcessModel();

		String orderNo = UUID.randomUUID().toString();
		orderModel = new OrderModel();
		orderModel.setOrderNo(orderNo);
		orderModel.setOrderTime(new Date());
		orderModel.setStatus(OrderStatus.dataEntry.value());
		orderModel = orderRepository.save(orderModel);

		UserModel userModel = userRepository.findOne(operator.getUserId());
		if (userModel == null) {
			throw new NotFoundException("Operator not found");
		}

		orderProcessModel.setOperator(userModel);
		ProcessInstance processInstance = activitiFacade.startProcessInstance(
				orderNo, processDefinitionId, null);

		orderProcessModel.setMainProcessDefinitionId(processDefinitionId);
		orderProcessModel.setActiveProcessDefinitionId(processDefinitionId);
		orderProcessModel.setMainProcessInstanceId(processInstance.getId());
		orderProcessModel.setActiveProcesssInstanceId(processInstance.getId());
		orderProcessModel.setOrder(orderModel);
		orderProcessModel = orderProcessRepository.save(orderProcessModel);
		orderProcessDto = orderProcessConverter.toDto(orderProcessModel);

		orderProcessDto = orderProcessAccessor.postProcessForPendingActivity(
				orderProcessDto, orderProcessModel,
				String.valueOf(operator.getUserId()),
				PendingActivityBuildOperation.ALL);
		LOGGER.info("startOrderProcess end:{}", orderProcessDto);
		return orderProcessDto;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto dataEntry(String orderNo, OrderDto order,
			UserDto operator) throws Exception {
		LOGGER.info("dataEntry start:{}", orderNo);
		Map<String, Object> variableMap = null;
		OrderProcessDto orderProcessDto = null;
		Set<OrderDetailsDto> orderDetailsSet = order.getOrderDetailsSet();
		CustomerDto customerDto = order.getCustomer();

		OrderProcessModel orderProcessModel = orderProcessRepository
				.findOne(findByOrderNo(orderNo));

		if (orderProcessModel == null) {
			throw new NotFoundException("Order not found by no[" + orderNo
					+ "]");
		}

		CustomerModel customerModel = customerConverter.toModel(customerDto);

		OrderModel orderModel = orderProcessModel.getOrder();
		orderModel.setCustomer(customerModel);
		orderModel.setQuantity(order.getQty());
		orderModel.setTotalPrice(order.getTotalPrice());
		orderModel.setShipAddress(order.getAddress());

		if (orderDetailsSet != null) {
			for (OrderDetailsDto orderDetailsDto : orderDetailsSet) {
				PizzashackModel pizzashackModel = pizzashackRepository
						.findOne(findByPizzashackName(orderDetailsDto
								.getPizzaName()));
				OrderPizzashackModel orderPizzashackModel = OrderPizzashackModel
						.getBuilder(orderModel, pizzashackModel,
								orderDetailsDto.getQty(),
								orderDetailsDto.getTotalPrice()).build();
				orderModel.addOrderPizzashack(orderPizzashackModel);
			}
		}

		Task pendingTask = activitiFacade.getActiviteTask(orderNo,
				orderProcessModel.getActiveProcessDefinitionId());
		if (pendingTask == null) {
			throw new NotFoundException("dataEntry task not found");
		}
		String taskName = pendingTask.getName();
		if (!activitiFacade.checkIfUserHasRightForGivenTask(orderNo, taskName,
				String.valueOf(operator.getUserId()))) {
			throw new Exception("User[" + operator.getUsername()
					+ "] has no right to process " + taskName + "");
		}
		orderProcessDto = orderProcessConverter.toDto(orderProcessModel);

		variableMap = new HashMap<String, Object>();
		variableMap.put(ORDER_MAIN_PROCESS_OBJ, orderProcessDto);
		taskService.complete(pendingTask.getId(), variableMap);

		orderProcessDto = orderProcessAccessor.postProcessForPendingActivity(
				orderProcessDto, orderProcessModel,
				String.valueOf(operator.getUserId()),
				PendingActivityBuildOperation.ALL);
		LOGGER.info("dataEntry end:{}", orderProcessDto);
		return orderProcessDto;
	}

	@Override
	public OrderProcessDto fillinBillingAccount(String orderNo,
			BillingDto billing, UserDto operator) throws Exception {
		
		return null;
	}

	@Override
	public OrderProcessDto claimOrderReviewTask(String orderNo,
			UserDto currentLoginUser) throws Exception {

		return null;
	}

	@Override
	public OrderProcessDto manualOrderReview(String orderNo,
			OrderReviewRecordDto reviewRecord) throws Exception {

		return null;
	}

	@Override
	public Set<OrderProcessDto> getAllTransTaskForCurrentUser(
			UserDto currentLoginUser) throws Exception {

		return null;
	}

}
