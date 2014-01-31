package co.nz.pizzashack.integration.processor;

import static co.nz.pizzashack.DistributorConstants.BILLING_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.data.predicates.UserPredicates.findByUsername;

import javax.annotation.Resource;
import javax.xml.bind.annotation.XmlElement;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.UserModel;
import co.nz.pizzashack.data.repository.UserRepository;
import co.nz.pizzashack.ds.OrderProcessDS;
import co.nz.pizzashack.integration.ws.BillingResponse;
import co.nz.pizzashack.wf.ActivitiFacade;

@Component
public class BillingInboundProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingInboundProcessor.class);

	@Resource
	private OrderProcessDS orderProcessDs;

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserConverter userConverter;

	@Resource
	private ActivitiFacade activitiFacade;

	private static final String DEFAULT_OPERATOR = "general";

	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.info("BillingInboundProcessor start:{} ");
		BillingDto billingDto = exchange.getIn().getBody(BillingDto.class);
		String orderNo = billingDto.getOrderNo();

		BillingResponse billingResponse = new BillingResponse(orderNo);

		UserModel userModel = userRepository
				.findOne(findByUsername(DEFAULT_OPERATOR));
		UserDto operator = userConverter.toDto(userModel);

		LOGGER.info("default operator:{} ", operator);

		OrderProcessDto orderProcessDto = orderProcessDs.fillinBillingAccount(
				orderNo, billingDto, operator);

		ProcessActivityDto pendingActivity = orderProcessDto
				.getPendingActivity();
		LOGGER.info("get pending activity:{}", pendingActivity);
		if (pendingActivity.getActivityId().equals("billingEntry")) {
			// billing failed
			String activeProcessInstanceId = orderProcessDto
					.getActiveProcesssInstanceId();
			Object variable = activitiFacade.getVariableFromCurrentProcess(
					activeProcessInstanceId, BILLING_MAIN_PROCESS_OBJ);
			billingDto = (BillingDto) variable;
			billingResponse.setBillingCode(billingDto.getBillingCode());
			billingResponse.setBillingMessage(billingDto.getBillingMessage());
		} else {
			billingResponse.setBillingCode("000");
		}
		LOGGER.info("billingResponse:{} ", billingResponse);
		exchange.getIn().setBody(billingResponse, BillingResponse.class);
		LOGGER.info("BillingInboundProcessor end:{} ");
	}

}
