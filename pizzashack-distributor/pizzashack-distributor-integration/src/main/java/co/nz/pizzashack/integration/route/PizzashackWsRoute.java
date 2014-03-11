package co.nz.pizzashack.integration.route;

import javax.annotation.Resource;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.ds.PizzashackDS;
import co.nz.pizzashack.integration.ws.FaultHandler;

@Component("pizzashackWsRoute")
public class PizzashackWsRoute extends RouteBuilder {

	public static final String ENDPOINT = "cxf:bean:pizzashackEndpoint";

	@Resource
	private PizzashackDS pizzashackDs;

	@Override
	public void configure() throws Exception {
		from(ENDPOINT).routeId(ENDPOINT).onException(Exception.class)
				.handled(true)
				.setFaultBody(method(FaultHandler.class, "createFault")).end()
				.to("log:input").bean(pizzashackDs, "getPizzashackByName")
				.to("log:output");
	}

}
