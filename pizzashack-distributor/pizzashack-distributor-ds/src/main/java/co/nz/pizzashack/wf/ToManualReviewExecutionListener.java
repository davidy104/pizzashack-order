package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.INDEPENDENT_PROCESS_FLAG;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_SUB_PROCESS_OBJ;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.OrderProcessDto;

@Component("toManualReviewExecutionListener")
public class ToManualReviewExecutionListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ToManualReviewExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("ToManualReviewExecutionListener start:{} ");
		LOGGER.info("eventName:{} ", execution.getEventName());
		boolean indenpendentProcess = true;
		indenpendentProcess = (Boolean) execution
				.getVariable(INDEPENDENT_PROCESS_FLAG);

		if (!indenpendentProcess) {
			workForOrderProcess(execution);
		} else {
			OrderProcessDto orderProcess = (OrderProcessDto) execution
					.getVariable(ORDER_SUB_PROCESS_OBJ);
			orderProcess.setExecutionId(execution.getId());
			orderProcess.getOrder().setStatus("pendingOnReview");
		}
	}

	private void workForOrderProcess(ExecutionListenerExecution execution) {
		ExecutionEntity parentExecutionEntity = null;
		String activitiProcessDefinitionId = execution.getProcessDefinitionId();
		String activitiProcesssInstanceId = execution.getProcessInstanceId();
		LOGGER.info("activitiProcessDefinitionId:{}",
				activitiProcessDefinitionId);
		LOGGER.info("activitiProcesssInstanceId:{}", activitiProcesssInstanceId);
		parentExecutionEntity = ((ExecutionEntity) execution)
				.getSuperExecution();

		// copy billing from current flow to its parent flow
		// copy current process instance info to OrderProcess
		OrderProcessDto orderProcess = (OrderProcessDto) parentExecutionEntity
				.getVariable(ORDER_MAIN_PROCESS_OBJ);
		orderProcess.setActiveProcesssInstanceId(activitiProcesssInstanceId);
		orderProcess.setActiveProcessDefinitionId(activitiProcessDefinitionId);
		orderProcess.setExecutionId(execution.getId());
		orderProcess.getOrder().setStatus("pendingOnReview");

		// when current flow triggered by its parent, we need to copy bizkey to
		// subprocess,especially when subflow paused,
		if (StringUtils.isEmpty(execution.getProcessBusinessKey())) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			executionEntity
					.setBusinessKey(orderProcess.getOrder().getOrderNo());
		}

		parentExecutionEntity.setVariable(ORDER_MAIN_PROCESS_OBJ, orderProcess);
	}
}
