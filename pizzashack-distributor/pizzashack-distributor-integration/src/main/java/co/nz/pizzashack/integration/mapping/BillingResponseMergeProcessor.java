package co.nz.pizzashack.integration.mapping;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;

@Component
public class BillingResponseMergeProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingResponseMergeProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		Message inMessage = exchange.getIn();
		BillingDto billingTrans = exchange.getProperty("BillingRequest",
				BillingDto.class);
		LOGGER.info("billingReq:{} ", billingTrans);
		BillingDto billingResp = inMessage.getBody(BillingDto.class);
		LOGGER.info("billingResp:{} ", billingResp);

		billingTrans.setBillingCode(billingResp.getBillingCode());
		billingTrans.setBillingMessage(billingResp.getBillingMessage());
		inMessage.setBody(billingTrans);
//		Integer billingProcessStatus = 0;
//		if(!billingTrans.getBillingCode().equals("000")){
//			billingProcessStatus = 1;
//			
//		}
//		exchange.setProperty("billingProcessStatus", billingProcessStatus);
	}

}
