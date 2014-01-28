package co.nz.pizzashack.ds;

import static co.nz.pizzashack.DistributorConstants.BILLING_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_SUB_PROCESS_OBJ;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.converter.OrderConverter;
import co.nz.pizzashack.data.converter.OrderProcessConverter;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderModel.OrderStatus;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRepository;
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
	private TaskService taskService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessAccessor.class);

	public enum PendingActivityBuildOperation {
		ACTIVITY_INCOMING, ACTIVITY_OUTGOING, TASK_DETAILS, ALL
	}

	public void mergeDtoToModelAftProcess(OrderProcessDto dto,
			OrderProcessModel model) {
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
	}

	public OrderProcessDto postProcessForPendingActivity(
			OrderProcessDto orderProcessDto,
			OrderProcessModel orderProcessModel, String operatorName,
			PendingActivityBuildOperation... loadPendingActivityOperations)
			throws Exception {
		OrderProcessDto resultDto = null;
		LOGGER.info("postProcessForFlow start:{} ", orderProcessDto);
		OrderDto orderDto = orderProcessDto.getOrder();
		String orderNo = orderDto.getOrderNo();
		String mainProcessDefinitionId = orderProcessDto
				.getMainProcessDefinitionId();
		String mainProcessInstanceId = orderProcessDto
				.getMainProcessInstanceId();

		if (!activitiFacade
				.ifProcessFinishted(orderNo, mainProcessDefinitionId)) {
			LOGGER.info("process pending ");
			boolean loadincoming = false;
			boolean loadoutgoing = false;
			boolean loadTaskDetails = false;

			Object variable = activitiFacade.getVariableFromCurrentProcess(
					mainProcessInstanceId, ORDER_MAIN_PROCESS_OBJ);
			if (variable != null) {
				resultDto = (OrderProcessDto) variable;
				LOGGER.info("get OrderProcessDto from flow:{} ", resultDto);
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
							resultDto.getActiveProcessDefinitionId(),
							resultDto.getActiveProcesssInstanceId(),
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
				} else if (loadTaskDetails) {
					this.buildTaskDetails(pendingTask, pendingActivity,
							resultDto.getActiveProcessDefinitionId());
				}
			} else if (pendingActivity.getType().equals("receiveTask")
					&& pendingActivity.getName().equals(
							"Receive Billing Result")) {
				// billing response, it might be bug , receiveTask can not be
				// triggered automatically by camel route
				this.signalBillingRecieveTask(resultDto);
				resultDto = (OrderProcessDto) activitiFacade
						.getVariableFromCurrentProcess(mainProcessInstanceId,
								ORDER_MAIN_PROCESS_OBJ);

			}

			resultDto.setPendingActivity(pendingActivity);
			this.mergeDtoToModelAftProcess(resultDto, orderProcessModel);
			LOGGER.info("after merge publishTransModel:{} ", orderProcessModel);
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
		if (!StringUtils.isEmpty(pendingTask.getAssignee())) {
			pendingActivity.setAssignee(pendingTask.getAssignee());
		} else {
			Map<String, Set<String>> candidatasInfo = activitiFacade
					.getActiviteTaskCandidateAssignmentInfo(taskDefinitionKey,
							processDefinitionId);
			if (candidatasInfo != null) {
				pendingActivity.setCandidateUsers(candidatasInfo
						.get(ActivitiFacade.CANDIDATE_USERS));
				pendingActivity.setCandidateGroups(candidatasInfo
						.get(ActivitiFacade.CANDIDATE_GROUPS));
			}
		}
	}

}
