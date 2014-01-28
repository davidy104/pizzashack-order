package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.BILLING_SUB_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.INDEPENDENT_PROCESS_FLAG;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;

import java.util.Map;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;

@Component("billingRespExecutionListener")
public class BillingRespExecutionListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingRespExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("BillingRespExecutionListener execute start:{}");

		BillingDto billingTrans = (BillingDto) execution
				.getVariable(BILLING_SUB_PROCESS_OBJ);

		LOGGER.info("get billing after integration:{}", billingTrans);

		Integer billingProcessStatus = 0;
		String code = billingTrans.getBillingCode();
		if (!code.equals("000")) {
			billingProcessStatus = 1;
		}
		execution.setVariable("billingProcessStatus", billingProcessStatus);

		// camel route trigger might have bug, we need to signal the receiveTask
		// manually outside. so need to copy current processInstance info to
		// OrderProcess
		boolean indenpendentProcess = true;
		indenpendentProcess = (Boolean) execution
				.getVariable(INDEPENDENT_PROCESS_FLAG);

		if (!indenpendentProcess) {
			this.workForOrderProcess(execution);
		}

		// print for testing
		Map<String, Object> variables = execution.getVariables();
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			LOGGER.info("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
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
