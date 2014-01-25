package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;

import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;

@Component("orderMainTransitionExecutionListener")
public class OrderMainTransitionExecutionListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderMainTransitionExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("orderMainUserTaskExecutionListener start:{} ");

		String executionId = execution.getId();

		OrderProcessDto orderProcess = (OrderProcessDto) execution
				.getVariable(ORDER_MAIN_PROCESS_OBJ);
		orderProcess.setExecutionId(executionId);
		OrderDto order = orderProcess.getOrder();

		PvmTransition transition = (PvmTransition) execution.getEventSource();
		PvmActivity nextActivity = transition.getDestination();
		String activityType = (String) nextActivity.getProperty("type");
		String activityName = (String) nextActivity.getProperty("name");
		String activityId = nextActivity.getId();

		LOGGER.info("nextActivity id:{}", activityId);
		LOGGER.info("nextActivity name:{}", activityName);
		LOGGER.info("nextActivity type:{}", activityType);

		if (activityType.equals("userTask")
				|| activityType.equals("receiveTask")) {

			if (activityId.equals("dataEntry")
					|| activityId.equals("externalDataEntry")) {
				order.setStatus("dataEntry");

			} else if (activityId.equals("billingEntry")) {
				order.setStatus("pendingOnBilling");

			}
		} else if (activityType.equals("callActivity")) {
			if (activityId.equals("billingCallactivity")) {
				execution.setVariable("billingIsIndependentProcess", false);
			} else if (activityId.equals("orderCoreCallactivity")) {
				execution.setVariable("coreIsIndependentProcess", false);
			}
		}
	}
}
