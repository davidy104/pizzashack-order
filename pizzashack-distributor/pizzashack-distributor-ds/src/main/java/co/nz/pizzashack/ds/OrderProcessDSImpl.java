package co.nz.pizzashack.ds;

import static co.nz.pizzashack.DistributorConstants.BILLING_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.REVIEW_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustEmail;
import static co.nz.pizzashack.data.predicates.OrderProcessPredicates.findByExecutionIds;
import static co.nz.pizzashack.data.predicates.PizzashackPredicates.findByPizzashackName;
import static co.nz.pizzashack.data.predicates.WorkflowPredicates.findByProcessDefinitionKeyAndCategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.CustomerConverter;
import co.nz.pizzashack.data.converter.OrderConverter;
import co.nz.pizzashack.data.converter.OrderProcessConverter;
import co.nz.pizzashack.data.converter.OrderReviewRecordConverter;
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
import co.nz.pizzashack.data.repository.CustomerRepository;
import co.nz.pizzashack.data.repository.OrderPizzashackRepository;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.data.repository.OrderReviewRecordRepository;
import co.nz.pizzashack.data.repository.PizzashackRepository;
import co.nz.pizzashack.data.repository.UserRepository;
import co.nz.pizzashack.data.repository.WorkflowRepository;
import co.nz.pizzashack.ds.OrderProcessAccessor.PendingActivityBuildOperation;
import co.nz.pizzashack.wf.ActivitiFacade;

@Service
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

	@Resource
	private OrderReviewRecordConverter orderReviewRecordConverter;

	@Resource
	private OrderReviewRecordRepository orderReviewRecordRepository;

	@Resource
	private OrderPizzashackRepository orderPizzashackRepository;

	@Resource
	private CustomerRepository customerRepository;

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
		orderProcessModel.setExecutionId(processInstance.getId());
		orderProcessModel.setCreateTime(new Date());
		orderProcessModel.setOrder(orderModel);
		orderProcessModel = orderProcessRepository.save(orderProcessModel);
		orderProcessDto = orderProcessConverter.toDto(orderProcessModel);

		orderProcessDto = orderProcessAccessor.postProcess(orderProcessDto,
				orderProcessModel, String.valueOf(operator.getUserId()),
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
		String custEmail = customerDto.getCustomerEmail();

		OrderProcessModel orderProcessModel = orderProcessAccessor
				.getOrderProcessByOrderNo(orderNo);

		CustomerModel customerModel = customerRepository
				.findOne(findByCustEmail(custEmail));
		if (customerModel == null) {
			customerModel = customerConverter.toModel(customerDto);
			customerModel = customerRepository.save(customerModel);
		}

		OrderModel orderModel = orderProcessModel.getOrder();
		orderModel.setCustomer(customerModel);
		orderModel.setQuantity(order.getQty());
		orderModel.setTotalPrice(order.getTotalPrice());
		orderModel.setShipAddress(order.getAddress());

		if (orderDetailsSet != null && orderDetailsSet.size() > 0) {
			List<OrderPizzashackModel> orderPizzashackModelList = new ArrayList<OrderPizzashackModel>();
			LOGGER.info("orderDetailsSet size:{} ", orderDetailsSet.size());
			for (OrderDetailsDto orderDetailsDto : orderDetailsSet) {
				PizzashackModel pizzashackModel = pizzashackRepository
						.findOne(findByPizzashackName(orderDetailsDto
								.getPizzaName()));
				OrderPizzashackModel orderPizzashackModel = OrderPizzashackModel
						.getBuilder(orderModel, pizzashackModel,
								orderDetailsDto.getQty(),
								orderDetailsDto.getTotalPrice()).build();
				LOGGER.info("added orderPizzashackModel:{} ",
						orderPizzashackModel);
				orderPizzashackModel = orderPizzashackRepository
						.save(orderPizzashackModel);
				orderPizzashackModelList.add(orderPizzashackModel);
			}
			LOGGER.info("after add detailsDto, size in model:{} ",
					orderPizzashackModelList.size());
			orderModel.setOrderPizzashackModels(orderPizzashackModelList);
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

		orderProcessDto = orderProcessAccessor.postProcess(orderProcessDto,
				orderProcessModel, String.valueOf(operator.getUserId()),
				PendingActivityBuildOperation.ALL);
		LOGGER.info("dataEntry end:{}", orderProcessDto);
		return orderProcessDto;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto fillinBillingAccount(String orderNo,
			BillingDto billing, UserDto operator) throws Exception {
		LOGGER.info("fillinBillingAccount start:{}", billing);
		OrderProcessDto orderProcessDto = null;
		OrderProcessModel orderProcessModel = orderProcessAccessor
				.getOrderProcessByOrderNo(orderNo);
		String userId = String.valueOf(operator.getUserId());

		orderProcessDto = doFillinBillingAccount(orderProcessModel, orderNo,
				billing, userId);

		if (!activitiFacade.ifProcessFinishted(orderNo,
				orderProcessModel.getMainProcessDefinitionId())) {
			LOGGER.info("process pending, build pending Activity:{} ");
			// flow paused
			orderProcessDto = orderProcessAccessor.postProcess(orderProcessDto,
					orderProcessModel, String.valueOf(operator.getUserId()),
					PendingActivityBuildOperation.ALL);
		} else {
			// flow completed
			LOGGER.info("process completed, get latest status of OrderProcess:{} ");
			orderProcessDto = orderProcessAccessor
					.getLatestOrderProcess(orderNo);
		}
		LOGGER.info("fillinBillingAccount end:{}", orderProcessDto);
		return orderProcessDto;
	}

	private OrderProcessDto doFillinBillingAccount(
			OrderProcessModel orderProcessModel, String orderNo,
			BillingDto billing, String userId) throws Exception {
		LOGGER.info("doFillinBillingAccount start:{} ");
		Map<String, Object> variableMap = null;
		Task pendingTask = activitiFacade.getActiviteTask(orderNo,
				orderProcessModel.getActiveProcessDefinitionId());

		if (pendingTask == null) {
			throw new NotFoundException("task not found");
		}

		String taskName = pendingTask.getName();
		LOGGER.info("current task:{} ", taskName);
		if (!activitiFacade.checkIfUserHasRightForGivenTask(orderNo, taskName,
				userId)) {
			throw new Exception("User[" + userId + "] has no right to process "
					+ taskName + "");
		}

		OrderProcessDto orderProcessDto = orderProcessConverter
				.toDto(orderProcessModel);
		variableMap = new HashMap<String, Object>();
		variableMap.put(ORDER_MAIN_PROCESS_OBJ, orderProcessDto);
		variableMap.put(BILLING_MAIN_PROCESS_OBJ, billing);
		taskService.complete(pendingTask.getId(), variableMap);
		LOGGER.info("doFillinBillingAccount end:{} ");
		return orderProcessDto;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public Long claimOrderReviewTask(String orderNo, UserDto currentLoginUser)
			throws Exception {
		LOGGER.info("claimOrderReviewTask start:{}", orderNo);

		OrderProcessModel orderProcessModel = orderProcessAccessor
				.getOrderProcessByOrderNo(orderNo);
		String activeProcessDefinitionId = null;
		String userId = String.valueOf(currentLoginUser.getUserId());
		activeProcessDefinitionId = orderProcessModel
				.getActiveProcessDefinitionId();

		Task task = activitiFacade.getActiviteTask(orderNo,
				activeProcessDefinitionId);
		if (task == null || !task.getName().equals("Manual underwriting")) {
			throw new NotFoundException("Task[Manual underwriting] not found");
		}
		String taskId = task.getId();
		String taskName = task.getName();
		LOGGER.info("taskId:{}", taskId);
		LOGGER.info("taskName:{}", taskName);

		if (!activitiFacade.checkIfUserHasRightForGivenTask(orderNo, taskName,
				userId)) {
			throw new Exception("User[" + currentLoginUser.getUsername()
					+ "] has no right for [" + taskName + "]");
		}

		taskService.setAssignee(taskId, userId);
		taskService.claim(taskId, userId);
		LOGGER.info("claimOrderReviewTask end:{}");
		return orderProcessModel.getOrderProcessId();
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = true)
	public boolean ifCurrentUserHasRightForTask(String orderNo,
			String taskName, UserDto currentLoginUser) throws Exception {
		return activitiFacade.checkIfUserHasRightForGivenTask(orderNo,
				taskName, String.valueOf(currentLoginUser.getUserId()));

	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto manualOrderReview(String orderNo,
			OrderReviewRecordDto reviewRecord, UserDto currentLoginUser)
			throws Exception {
		LOGGER.info("manualOrderReview start:{} ", reviewRecord);
		OrderProcessDto orderProcessDto = null;
		OrderProcessModel orderProcessModel = orderProcessAccessor
				.getOrderProcessByOrderNo(orderNo);
		UserModel operatorModel = orderProcessModel.getOperator();
		UserDto operator = userConverter.toDto(operatorModel);

		orderProcessDto = this.doManualOrderReview(orderProcessModel, orderNo,
				reviewRecord, currentLoginUser);

		if (!activitiFacade.ifProcessFinishted(orderNo,
				orderProcessModel.getMainProcessDefinitionId())) {
			LOGGER.info("process pending, build pending Activity:{} ");
			// flow paused
			orderProcessDto = orderProcessAccessor.postProcess(orderProcessDto,
					orderProcessModel, String.valueOf(operator.getUserId()),
					PendingActivityBuildOperation.ALL);
		} else {
			// flow completed
			LOGGER.info("process completed, get latest status of OrderProcess:{} ");
			orderProcessDto = orderProcessAccessor
					.getLatestOrderProcess(orderNo);
		}
		LOGGER.info("manualOrderReview end:{} ", orderProcessDto);
		return orderProcessDto;
	}

	private OrderProcessDto doManualOrderReview(
			OrderProcessModel orderProcessModel, String orderNo,
			OrderReviewRecordDto reviewRecord, UserDto currentLoginUser)
			throws Exception {
		LOGGER.info("doManualOrderReview start:{} ");
		Map<String, Object> variableMap = null;
		OrderProcessDto orderProcessDto = null;

		Task task = activitiFacade.getActiviteTask(orderNo,
				orderProcessModel.getActiveProcessDefinitionId());
		if (task == null || !task.getName().equals("Manual underwriting")) {
			throw new NotFoundException("Task[Manual underwriting] not found");
		}

		if (!activitiFacade.checkIfUserHasRightForGivenTask(orderNo,
				task.getName(), String.valueOf(currentLoginUser.getUserId()))) {
			throw new Exception("User[" + currentLoginUser.getUsername()
					+ "] has no right for [" + task.getName() + "]");
		}

		orderProcessDto = orderProcessConverter.toDto(orderProcessModel);
		String reviewResult = reviewRecord.getReviewResult();
		LOGGER.info("reviewResult:{} ", reviewResult);
		Integer orderReviewStatus = 0;
		if (reviewResult.equals("reject")) {
			orderReviewStatus = 1;
		} else if (reviewResult.equals("pending")) {
			orderReviewStatus = 2;
		}

		variableMap = new HashMap<String, Object>();
		variableMap.put(REVIEW_SUB_PROCESS_OBJ, reviewRecord);
		variableMap.put(ORDER_SUB_PROCESS_OBJ, orderProcessDto);
		variableMap.put("orderReviewStatus", orderReviewStatus);
		taskService.complete(task.getId(), variableMap);
		LOGGER.info("doManualOrderReview end:{} ");
		return orderProcessDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(value = "localTxManager", readOnly = true)
	public Set<OrderProcessDto> getAllTransTaskForCurrentUser(
			UserDto currentLoginUser) throws Exception {
		LOGGER.info("getAllTransTaskForCurrentUser start:{} ", currentLoginUser);
		Set<OrderProcessDto> orderProcessDtoSet = null;
		List<OrderProcessModel> modelList = null;
		List<Task> taskList = activitiFacade.getAllTasksForUser(String
				.valueOf(currentLoginUser.getUserId()));
		if (taskList != null && taskList.size() > 0) {
			LOGGER.info("tasks size:{}", taskList.size());
			Set<String> taskExecutionIds = new HashSet<String>();
			for (Task task : taskList) {
				taskExecutionIds.add(task.getExecutionId());
			}

			Iterable<OrderProcessModel> resultIterable = orderProcessRepository
					.findAll(findByExecutionIds(taskExecutionIds));
			if (resultIterable != null) {
				orderProcessDtoSet = new HashSet<OrderProcessDto>();
				modelList = IteratorUtils.toList(resultIterable.iterator());
				for (OrderProcessModel model : modelList) {
					orderProcessDtoSet.add(orderProcessConverter.toDto(model));
				}
			}
		}
		LOGGER.info("getAllTransTaskForCurrentUser end:{} ");
		return orderProcessDtoSet;
	}

}
