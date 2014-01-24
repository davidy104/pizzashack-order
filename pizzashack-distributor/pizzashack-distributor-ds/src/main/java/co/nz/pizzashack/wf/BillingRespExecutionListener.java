package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.BILLING_SUB_PROCESS_OBJ;

import java.util.Map;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;

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

		// print for testing
		Map<String, Object> variables = execution.getVariables();
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			LOGGER.info("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}
	}
}
