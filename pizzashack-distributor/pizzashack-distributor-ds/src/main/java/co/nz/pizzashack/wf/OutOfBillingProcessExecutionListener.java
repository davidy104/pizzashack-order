package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.BILLING_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;

@Component("outOfBillingProcessExecutionListener")
public class OutOfBillingProcessExecutionListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutOfBillingProcessExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		BillingDto billingDto = (BillingDto) execution
				.getVariable("billingTransOut");
		LOGGER.info("after billing process, billingDto:{} ", billingDto);
		execution.setVariable(BILLING_MAIN_PROCESS_OBJ, billingDto);

		OrderProcessDto orderProcess = (OrderProcessDto) execution
				.getVariable("orderOutTrans");
		LOGGER.info("after core process, orderProcess:{} ", orderProcess);
		orderProcess.setActiveProcessDefinitionId(execution
				.getProcessDefinitionId());
		orderProcess.setActiveProcesssInstanceId(execution
				.getProcessInstanceId());
		orderProcess.setExecutionId(execution.getId());
		execution.setVariable(ORDER_MAIN_PROCESS_OBJ, orderProcess);
	}
}
