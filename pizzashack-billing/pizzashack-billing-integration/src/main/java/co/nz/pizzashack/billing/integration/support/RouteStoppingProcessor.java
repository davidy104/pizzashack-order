package co.nz.pizzashack.billing.integration.support;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RouteStoppingProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RouteStoppingProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("RouteStoppingProcessor start:{}");
		final String routeName = exchange.getIn().getBody(String.class);
		final CamelContext context = exchange.getContext();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					context.stopRoute(routeName);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}).start();

	}

}
