package co.nz.pizzashack.wf;

import java.util.Map;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("orderExternalDataEntryExecutionListener")
public class OrderExternalDataEntryExecutionListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderExternalDataEntryExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("OrderExternalDataEntryExecutionListener execute start:{}");
		// print for testing
		Map<String, Object> variables = execution.getVariables();
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			LOGGER.info("Key : " + entry.getKey() + " Value : "
					+ entry.getValue());
		}
	}
}
