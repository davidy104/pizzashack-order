package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.BILLING_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.REVIEW_MAIN_PROCESS_OBJ;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;

@Component("emailExecutionListener")
public class EmailExecutionListener {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmailExecutionListener.class);

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("EmailExecutionListener start:{} ");
		OrderProcessDto orderProcess = (OrderProcessDto) execution
				.getVariable(ORDER_MAIN_PROCESS_OBJ);
		String[] contents = null;
		String content = null;

		OrderDto order = orderProcess.getOrder();
		String orderNo = order.getOrderNo();
		CustomerDto customer = order.getCustomer();

		String emailAddress = customer.getCustomerEmail();
		String customerName = customer.getCustomerName();

		execution.setVariable("emailToAddress", emailAddress);
		execution.setVariable("emailCustomerName", customerName);

		if (order.getStatus().equals("rejected")) {
			LOGGER.info("prepare email info for rejected case:{} ");
			if (execution.getVariable(REVIEW_MAIN_PROCESS_OBJ) != null) {
				OrderReviewRecordDto orderReviewRecord = (OrderReviewRecordDto) execution
						.getVariable(REVIEW_MAIN_PROCESS_OBJ);
				content = "Your order is rejected due to ["
						+ orderReviewRecord.getReviewResult() + "]";
				contents = new String[] { content };
			}
		} else if (order.getStatus().equals("delivered")) {
			LOGGER.info("prepare email info for delivered case:{} ");
			content = "Your order is delivered successfully, order-no:"
					+ orderNo + "";
			contents = new String[] { content };
		} else if (order.getStatus().equals("pendingOnBilling")) {
			LOGGER.info("prepare email info for billing failed case:{} ");
			// billing auto process rejected and turn to billing entry again
			BillingDto billing = (BillingDto) execution
					.getVariable(BILLING_MAIN_PROCESS_OBJ);
			content = "Your order billing process is rejected, due to ["
					+ billing.getBillingMessage() + "]";
		}

		execution.setVariable("emailContents", contents);
		LOGGER.info("EmailExecutionListener end:{} ");
	}
}
