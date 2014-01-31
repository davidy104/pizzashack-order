package co.nz.pizzashack.integration.route;

import javax.annotation.Resource;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.integration.processor.BillingInboundProcessor;
import co.nz.pizzashack.integration.ws.FaultHandler;

@Component
public class BillingInboundWsRoute extends RouteBuilder {

	public static final String ENDPOINT = "cxf:bean:billingInboundEndpoint";

	@Resource
	private BillingInboundProcessor billingInboundProcessor;

	@Override
	public void configure() throws Exception {

		from(ENDPOINT).routeId(ENDPOINT).onException(Exception.class)
				.handled(true)
				.setFaultBody(method(FaultHandler.class, "createFault")).end()
				.to("log:input").process(billingInboundProcessor);
	}
}
