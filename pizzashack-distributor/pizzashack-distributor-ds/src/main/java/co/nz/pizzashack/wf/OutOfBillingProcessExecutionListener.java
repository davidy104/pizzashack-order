package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.BILLING_MAIN_PROCESS_OBJ;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;

@Component("outOfBillingProcessExecutionListener")
public class OutOfBillingProcessExecutionListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutOfBillingProcessExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		BillingDto billingDto = (BillingDto) execution
				.getVariable("billingTransOut");
		LOGGER.info("after billing process, billingDto:{} ", billingDto);
		execution.setVariable(BILLING_MAIN_PROCESS_OBJ, billingDto);
	}
}
