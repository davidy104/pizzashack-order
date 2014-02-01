package co.nz.pizzashack.ds;

import static co.nz.pizzashack.DistributorConstants.BILLING_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.data.predicates.OrderProcessPredicates.findByOrderNo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.OrderConverter;
import co.nz.pizzashack.data.converter.OrderProcessConverter;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderDetailsDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.model.DepartmentModel;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderModel.OrderStatus;
import co.nz.pizzashack.data.model.OrderPizzashackModel;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.model.UserModel;
import co.nz.pizzashack.data.repository.DepartmentRepository;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.data.repository.UserRepository;
import co.nz.pizzashack.wf.ActivitiFacade;

@Component
public class OrderProcessAccessor {
	@Resource
	private ActivitiFacade activitiFacade;

	@Resource
	private OrderProcessConverter orderProcessConverter;

	@Resource
	private OrderProcessRepository orderProcessRepository;

	@Resource
	private OrderConverter orderConverter;

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private DepartmentRepository departmentRepository;

	@Resource
	private TaskService taskService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessAccessor.class);

	public enum PendingActivityBuildOperation {
		ACTIVITY_INCOMING, ACTIVITY_OUTGOING, TASK_DETAILS, ALL
	}

	public void mergeDtoToModelAftProcess(OrderProcessDto dto,
			OrderProcessModel model, boolean mergeCalculationResult) {
		model.setActiveProcessDefinitionId(dto.getActiveProcessDefinitionId());
		model.setActiveProcesssInstanceId(dto.getActiveProcesssInstanceId());
		model.setExecutionId(dto.getExecutionId());

		OrderDto order = dto.getOrder();
		OrderModel orderModel = model.getOrder();

		if (order.getStatus().equals("dataEntry")) {
			orderModel.setStatus(OrderStatus.dataEntry.value());
		} else if (order.getStatus().equals("pendingOnReview")) {
			orderModel.setStatus(OrderStatus.pendingOnReview.value());
		} else if (order.getStatus().equals("pendingOnBilling")) {
			orderModel.setStatus(OrderStatus.pendingOnBilling.value());
		}

		if (mergeCalculationResult) {
			LOGGER.info("merge calculation result");
			LOGGER.info("total price:{} ", order.getTotalPrice());
			LOGGER.info("qty:{} ", order.getQty());
			orderModel.setTotalPrice(order.getTotalPrice());
			orderModel.setQuantity(order.getQty());

			Set<OrderDetailsDto> orderDetailsSet = order.getOrderDetailsSet();
			LOGGER.info("orderDetailsSet.size():{}", orderDetailsSet.size());
			List<OrderPizzashackModel> orderPizzashackModels = orderModel
					.getOrderPizzashackModels();

			if (orderDetailsSet != null && orderDetailsSet.size() > 0) {
				for (OrderDetailsDto orderDetails : orderDetailsSet) {
					Long dtoId = orderDetails.getOrderDetailId();
					for (OrderPizzashackModel orderPizzashackModel : orderPizzashackModels) {
						Long modelId = orderPizzashackModel
								.getOrderPizzashackId();
						if (dtoId.longValue() == modelId.longValue()) {
							orderPizzashackModel.setQty(orderDetails.getQty());
							orderPizzashackModel.setTotalPrice(orderDetails
									.getTotalPrice());
							break;
						}
					}
				}
			}
		}

	}

	public OrderProcessDto postProcess(OrderProcessDto orderProcessDto,
			OrderProcessModel orderProcessModel, String operatorName,
			PendingActivityBuildOperation... loadPendingActivityOperations)
			throws Exception {
		OrderProcessDto resultDto = orderProcessDto;
		LOGGER.info("postProcessForFlow start:{} ", orderProcessDto);
		OrderDto orderDto = orderProcessDto.getOrder();
		String orderNo = orderDto.getOrderNo();
		boolean ifMergeRequired = false;
		boolean mergeCalculationResult = false;
		String mainProcessInstanceId = orderProcessDto
				.getMainProcessInstanceId();
		String mainProcessDefinitionId = orderProcessDto
				.getMainProcessDefinitionId();
		String activeProcessDefinitionId = orderProcessDto
				.getActiveProcessDefinitionId();
		String activeProcessInstanceId = orderProcessDto
				.getActiveProcesssInstanceId();

		LOGGER.info("process pending ");
		boolean loadincoming = false;
		boolean loadoutgoing = false;
		boolean loadTaskDetails = false;

		if (!activitiFacade
				.ifProcessFinishted(orderNo, mainProcessDefinitionId)) {
			Object variable = activitiFacade.getVariableFromCurrentProcess(
					mainProcessInstanceId, ORDER_MAIN_PROCESS_OBJ);
			if (variable != null) {
				resultDto = (OrderProcessDto) variable;
				LOGGER.info("get OrderProcessDto from flow:{} ", resultDto);

				activeProcessDefinitionId = resultDto
						.getActiveProcessDefinitionId();
				activeProcessInstanceId = resultDto
						.getActiveProcesssInstanceId();
				ifMergeRequired = true;
			}

			if (loadPendingActivityOperations != null
					&& loadPendingActivityOperations.length > 0) {
				for (PendingActivityBuildOperation loadPendingActivityOperation : loadPendingActivityOperations) {
					if (loadPendingActivityOperation == PendingActivityBuildOperation.ALL) {
						loadincoming = true;
						loadoutgoing = true;
						loadTaskDetails = true;
						break;
					} else if (loadPendingActivityOperation == PendingActivityBuildOperation.TASK_DETAILS) {
						loadTaskDetails = true;
					} else if (loadPendingActivityOperation == PendingActivityBuildOperation.ACTIVITY_INCOMING) {
						loadincoming = true;
					} else if (loadPendingActivityOperation == PendingActivityBuildOperation.ACTIVITY_OUTGOING) {
						loadoutgoing = true;
					}
				}
			}

			ProcessActivityDto pendingActivity = activitiFacade
					.getExecutionActivityBasicInfo(orderNo,
							activeProcessDefinitionId, activeProcessInstanceId,
							loadincoming, loadoutgoing);
			LOGGER.info("pending activity:{}", pendingActivity);

			if (pendingActivity.getType().equals("userTask")) {
				Task pendingTask = this.getPendingTask(
						pendingActivity.getName(), orderNo);
				if (pendingActivity.getName().equals("Data entry")
						|| pendingActivity.getName().equals("Billing fill in")) {
					LOGGER.info("assign operator[" + operatorName
							+ "] to task[" + pendingTask.getName() + "]");
					pendingTask.setAssignee(operatorName);
					taskService.claim(pendingTask.getId(), operatorName);
					pendingActivity.setAssignee(operatorName);

				}

				if (pendingActivity.getName().equals("Manual underwriting")
						|| pendingActivity.getName().equals("Billing fill in")) {
					mergeCalculationResult = true;
				}

				if (loadTaskDetails) {
					this.buildTaskDetails(pendingTask, pendingActivity,
							activeProcessDefinitionId);
				}
			} else if (pendingActivity.getType().equals("receiveTask")
					&& pendingActivity.getName().equals(
							"Receive Billing Result")) {
				// billing response, it might be bug , receiveTask can not be
				// triggered automatically by camel route
				LOGGER.info("*************************pending on receiveBillingResponse task:{}");
				this.signalBillingRecieveTask(resultDto);
				resultDto = postProcess(resultDto, orderProcessModel,
						operatorName, loadPendingActivityOperations);
			}

			resultDto.setPendingActivity(pendingActivity);
			if (ifMergeRequired) {
				this.mergeDtoToModelAftProcess(resultDto, orderProcessModel,
						mergeCalculationResult);
				LOGGER.info("after merge orderProcessModel:{} ",
						orderProcessModel);
			}
		} else {
			resultDto = this.getLatestOrderProcess(orderNo);
			resultDto.setPendingActivity(null);
		}

		LOGGER.info("postProcessForFlow end:{} ", resultDto);
		return resultDto;
	}

	private void signalBillingRecieveTask(OrderProcessDto orderProcessDto) {
		BillingDto billingTrans = (BillingDto) activitiFacade
				.getVariableFromCurrentProcess(
						orderProcessDto.getActiveProcesssInstanceId(),
						BILLING_SUB_PROCESS_OBJ);

		Integer billingProcessStatus = 0;
		String code = billingTrans.getBillingCode();
		if (!code.equals("000")) {
			billingProcessStatus = 1;
		}

		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put("billingProcessStatus", billingProcessStatus);
		processVariables.put(ORDER_SUB_PROCESS_OBJ, orderProcessDto);
		processVariables.put(BILLING_SUB_PROCESS_OBJ, billingTrans);
		activitiFacade.signalProcessInstance(orderProcessDto.getExecutionId(),
				processVariables);
	}

	public Task getPendingTask(String activityName, String bizKey) {
		Task pendingTask = activitiFacade.getActiveTaskByNameAndBizKey(
				activityName, bizKey);
		return pendingTask;
	}

	public void buildTaskDetails(Task pendingTask,
			ProcessActivityDto pendingActivity, String processDefinitionId) {
		String taskDefinitionKey = pendingTask.getTaskDefinitionKey();
		UserModel userModel = null;
		DepartmentModel deptModel = null;
		Set<String> candidataUserNames = null;
		Set<String> candidataDepNames = null;
		String assignee = pendingTask.getAssignee();
		if (!StringUtils.isEmpty(assignee)) {
			userModel = userRepository.findOne(Long.valueOf(assignee));
			pendingActivity.setAssignee(userModel.getUsername());
		} else {
			Map<String, Set<String>> candidatasInfo = activitiFacade
					.getActiviteTaskCandidateAssignmentInfo(taskDefinitionKey,
							processDefinitionId);
			if (candidatasInfo != null) {
				Set<String> candidataUsers = candidatasInfo
						.get(ActivitiFacade.CANDIDATE_USERS);
				Set<String> candidataGroups = candidatasInfo
						.get(ActivitiFacade.CANDIDATE_GROUPS);

				if (candidataUsers != null && candidataUsers.size() > 0) {
					candidataUserNames = new HashSet<String>();
					for (String candidataUser : candidataUsers) {
						userModel = userRepository.findOne(Long
								.valueOf(candidataUser));
						candidataUserNames.add(userModel.getUsername());
					}
				}

				if (candidataGroups != null && candidataGroups.size() > 0) {
					candidataDepNames = new HashSet<String>();
					for (String candidataGroup : candidataGroups) {
						deptModel = departmentRepository.findOne(Long
								.valueOf(candidataGroup));
						candidataDepNames.add(deptModel.getDeptName());
					}
				}

				pendingActivity.setCandidateUsers(candidataUserNames);
				pendingActivity.setCandidateGroups(candidataDepNames);
			}
		}
	}

	public OrderProcessModel getOrderProcessByOrderNo(String orderNo)
			throws NotFoundException {
		OrderProcessModel orderProcessModel = orderProcessRepository
				.findOne(findByOrderNo(orderNo));

		if (orderProcessModel == null) {
			throw new NotFoundException("Order not found by no[" + orderNo
					+ "]");
		}
		LOGGER.info("get OrderProcess from db:{} ", orderProcessModel);
		return orderProcessModel;
	}

	public OrderProcessDto getLatestOrderProcess(String orderNo)
			throws Exception {
		OrderProcessModel model = this.getOrderProcessByOrderNo(orderNo);
		return orderProcessConverter.toDto(model);
	}

}
