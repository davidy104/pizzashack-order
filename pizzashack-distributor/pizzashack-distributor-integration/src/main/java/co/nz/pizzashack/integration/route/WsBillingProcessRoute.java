package co.nz.pizzashack.integration.route;

import javax.annotation.Resource;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.integration.mapping.BillingProcessReqTransformer;
import co.nz.pizzashack.integration.mapping.BillingProcessRespTransformer;
import co.nz.pizzashack.integration.mapping.BillingVariableTransformer;
import co.nz.pizzashack.integration.utils.SleepBean;

//@Component
public class WsBillingProcessRoute extends RouteBuilder {

	public static final String ENDPOINT = "activiti:orderBillingProcess:billingIntegration?copyVariablesToBodyAsMap=true";

	public static final String WS_ENDPOINT = "cxf:bean:billingAccountEndpoint?dataFormat=POJO";

	public static final String BILLING_INTEGRATION_ENDPOINT = "direct:billingIntegration";

	@Resource
	private BillingVariableTransformer billingVariableTransformer;

	@Resource
	private SleepBean sleepBean;

	@Resource
	private BillingProcessReqTransformer billingProcessReqTransformer;

	@Resource
	private BillingProcessRespTransformer billingProcessRespTransformer;

	@Override
	public void configure() throws Exception {
		from(ENDPOINT)
				.routeId(ENDPOINT)
				.to("log:input")
				.setHeader(
						"destination",
						constant("activiti:orderBillingProcess:receiveBillingResult"))
				.to(BILLING_INTEGRATION_ENDPOINT);

		from(BILLING_INTEGRATION_ENDPOINT)
				.routeId(BILLING_INTEGRATION_ENDPOINT)
				.transform(billingVariableTransformer)
				.setHeader("messageId", simple("${body.billingRequestId}"))
				.to("direct:doBillingIntegration")
				// .to("direct:receiveBillingQueue");
				.wireTap("direct:receiveBillingQueue")
				.executorServiceRef("genericThreadPool");

		from("direct:receiveBillingQueue")
				.routeId("direct:receiveBillingQueue").to("log:input")
				.recipientList(header("destination"));

		from("direct:doBillingIntegration")
				.setExchangePattern(ExchangePattern.InOut)
				.setProperty("BillingRequest", simple("${body}"))
				.transform(billingProcessReqTransformer)
				.setHeader("operationName", constant("billingProcess"))
				.to(WS_ENDPOINT).to("log:myLog?showAll=true")
				.transform(billingProcessRespTransformer)
				.bean(sleepBean, "sleep");
	}

}
