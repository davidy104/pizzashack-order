package co.nz.pizzashack.billing.integration.route;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.billing.ds.AccountDS;
import co.nz.pizzashack.billing.integration.mapping.BillingFormatTransformer;

@Component("billingRoute")
public class BillingRoute extends RouteBuilder {

	public static final String ENDPOINT = "jms:queue:pizzashackBillingInbound?transacted=true";
	public static final String OUTBOUND_END_POINT = "jms:queue:pizzashackBillingOutbound?jmsMessageType=Text";
	public static final String ROUTEID = "billingRoute";

	public static final String BILLING_DUPLICATED_ENDPOINT = "direct:billingDuplicatedProcess";
	public static final String BILLING_REQULAR_ENDPOINT = "direct:billingRegularProcess";

	@Resource
	private JdbcMessageIdRepository accountJdbcMessageIdRepository;

	@Resource
	private BillingFormatTransformer billingFormatTransformer;

	@Resource
	private AccountDS accountDs;

	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {

		from(ENDPOINT)
				.routeId(ROUTEID)
				.setExchangePattern(ExchangePattern.InOnly)
				.onException(Exception.class, ExchangeTimedOutException.class)
				.handled(true)
				.end()
				.log("Received message ${header[messageId]}")
				.transacted("PROPAGATION_REQUIRED")
				.idempotentConsumer(header("messageId"),
						accountJdbcMessageIdRepository).skipDuplicate(false)
				.choice().when(property(Exchange.DUPLICATE_MESSAGE))
				.to(BILLING_DUPLICATED_ENDPOINT).otherwise()
				.to(BILLING_REQULAR_ENDPOINT).endChoice()
				.log("Signalled to stop route");

		from(BILLING_DUPLICATED_ENDPOINT)
				.routeId(BILLING_DUPLICATED_ENDPOINT)
				.to("log:co.nz.pizzashack.billing.integration.route.BillingRoute?showAll=true&level=INFO")
				// mock endpoint only for testing
				.to("mock:duplicate")
				.to("sql:select TRANS_NO from T_BILLING_REQUEST where message_id = :#messageId")
				.setHeader("transNo", simple("${body.get(0).get('TRANS_NO')}"))
				.log("transNo from Header ${header[transNo]}")
				.to("sql:select a.*, b.ACCOUNT_NO from T_ACCOUNT_TRANSACTION a, T_ACCOUNT b where a.ACCOUNT_TRANS_NO = :#transNo and a.ACCOUNT_ID = b.ACCOUNT_ID")
				.setBody(simple("${body.get(0)}"))
				.log("get processed billing meta data ${body}")
				.bean(billingFormatTransformer,
						"respXmlMarshalFromAccountTransQueryMap")
				.log("after marshal ${body}")
				.setHeader("duplicatedBillingRequest", constant("yes"))
				.to(OUTBOUND_END_POINT).endChoice();

		from(BILLING_REQULAR_ENDPOINT)
				.routeId(BILLING_REQULAR_ENDPOINT)
				.bean(billingFormatTransformer, "billingReqXmlUnmarshal")
				.bean(accountDs, "deduct")
				.choice()
				.when(simple("${body.transactionNo} != null"))
				.setHeader("transactionNo", simple("${body.transactionNo}"))
				.to("sql:update T_BILLING_REQUEST set TRANS_NO = :#transactionNo where message_id = :#messageId")
				.endChoice()
				.bean(billingFormatTransformer, "respXmlMarshal")
				.to("log:output")
				.to(OUTBOUND_END_POINT)
				.to("controlbus:route?routeId=" + BILLING_REQULAR_ENDPOINT
						+ "&action=stop&async=true");

	}

}
