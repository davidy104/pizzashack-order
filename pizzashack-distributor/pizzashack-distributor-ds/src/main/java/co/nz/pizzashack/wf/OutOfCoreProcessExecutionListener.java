package co.nz.pizzashack.wf;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.OrderProcessDto;

import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;

@Component("outOfCoreProcessExecutionListener")
public class OutOfCoreProcessExecutionListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OutOfCoreProcessExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
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
