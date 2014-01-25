package co.nz.pizzashack.integration.mapping;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.AccountDto;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.integration.ws.client.stub.BillingTransactionDto;

@Component
public class BillingProcessReqTransformer implements Expression {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingProcessReqTransformer.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T evaluate(Exchange exchange, Class<T> type) {
		LOGGER.info("BillingProcessReqTransformer start:{}");
		BillingDto billingDto = exchange.getIn().getBody(BillingDto.class);
		AccountDto accountDto = billingDto.getAccount();
		
		BillingTransactionDto billingTransaction = new BillingTransactionDto();
		co.nz.pizzashack.integration.ws.client.stub.AccountDto account = new co.nz.pizzashack.integration.ws.client.stub.AccountDto();
		account.setAccountNo(accountDto.getAccountNo());
		account.setSecurityNo(accountDto.getSecurityNo());
		account.setExpireDate(accountDto.getExpireDate());
		account.setAccountType(accountDto.getPaymode());

		LOGGER.info("account:{} ", account);

		billingTransaction.setAccount(account);
		billingTransaction.setBillingAmount(String.valueOf(billingDto
				.getBillingAmount()));
		billingTransaction.setCreateTime(billingDto.getBillingTime());

		LOGGER.info("to billingTransaction transform end:{}",
				billingTransaction);
		return (T) billingTransaction;
	}

}
