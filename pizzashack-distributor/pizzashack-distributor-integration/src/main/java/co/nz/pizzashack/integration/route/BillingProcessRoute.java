package co.nz.pizzashack.integration.route;

import javax.annotation.Resource;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.integration.mapping.BillingFormatTransformer;
import co.nz.pizzashack.integration.mapping.BillingResponseMergeProcessor;
import co.nz.pizzashack.integration.mapping.BillingVariableTransformer;

@Component
public class BillingProcessRoute extends RouteBuilder {

	public static final String MQ_ENDPOINT = "jms:queue:pizzashackBillingInbound?transacted=true&replyTo=pizzashackBillingOutbound&replyToType=Exclusive&requestTimeout=10s";

	public static final String BILLING_INTEGRATION_ENDPOINT = "direct:billingIntegration";

	@Resource
	private BillingVariableTransformer billingVariableTransformer;

	@Resource
	private BillingFormatTransformer billingFormatTransformer;

	@Resource
	private BillingResponseMergeProcessor billingResponseMergeProcessor;

	@Override
	public void configure() throws Exception {

		from(
				"activiti:orderBillingProcess:billingIntegration?copyVariablesToBodyAsMap=true")
				.routeId("activitiBillingProcess")
				.to("log:input")
				.to(BILLING_INTEGRATION_ENDPOINT)
				.to("controlbus:route?routeId=activitiBillingProcess&action=stop&async=true");
		// .wireTap(BILLING_INTEGRATION_ENDPOINT)
		// .executorServiceRef("genericThreadPool");

		from(BILLING_INTEGRATION_ENDPOINT)
				.routeId(BILLING_INTEGRATION_ENDPOINT)
				.transform(billingVariableTransformer)
				.setHeader("messageId", simple("${body.billingRequestId}"))
				.to("direct:doBillingIntegration")
				.wireTap("direct:receiveBillingQueue")
				.executorServiceRef("genericThreadPool");

		from("direct:receiveBillingQueue")
				.routeId("direct:receiveBillingQueue")
				.to("log:input")
				.to("activiti:orderBillingProcess:receiveBillingResult")
				.to("controlbus:route?routeId=direct:receiveQueue&action=stop&async=true");

		from("direct:doBillingIntegration")
				.setExchangePattern(ExchangePattern.InOut)
				.setProperty("BillingRequest", simple("${body}"))
				.bean(billingFormatTransformer, "billingReqXmlMarshal")
				.to("log:myLog?showAll=true").to(MQ_ENDPOINT)
				.log("jmsMsgId set = ${in.header.JMSMessageId}")
				.to("log:myLog?showAll=true")
				.bean(billingFormatTransformer, "billingRespXmalUnmarshal")
				.process(billingResponseMergeProcessor);
	}
}
