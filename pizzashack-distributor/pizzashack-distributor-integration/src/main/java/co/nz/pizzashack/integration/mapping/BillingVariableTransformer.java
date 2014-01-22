package co.nz.pizzashack.integration.mapping;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.BillingDto;

@Component
public class BillingVariableTransformer implements Expression {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingVariableTransformer.class);

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		Map variablesFromProcess = exchange.getIn().getBody(Map.class);
		BillingDto billing = (BillingDto) variablesFromProcess
				.get("billingTrans");
		LOGGER.info("after transform billing:{} ", billing);
		return (T) billing;
	}

}
