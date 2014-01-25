package co.nz.pizzashack.integration.mapping;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.integration.ws.client.stub.AccountTransactionRespDto;

@Component
public class BillingProcessRespTransformer implements Expression {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingProcessRespTransformer.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		LOGGER.debug("BillingProcessRespTransformer start:{}");
		AccountTransactionRespDto accountTransactionResp = exchange.getIn()
				.getBody(AccountTransactionRespDto.class);
		LOGGER.info("accountTransactionResp:{} ", accountTransactionResp);

		BillingDto billingDto = exchange.getProperty("BillingRequest",
				BillingDto.class);
		billingDto.setBillingCode(accountTransactionResp.getCode());
		billingDto.setBillingMessage(accountTransactionResp.getReasons());

		LOGGER.info("BillingProcessRespTransformer end:{} ", billingDto);
		return (T) billingDto;
	}

}
