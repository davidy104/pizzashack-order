package co.nz.pizzashack.client.integration.route;

import javax.annotation.Resource;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.client.integration.mapping.OrderProcessFormatTransformer;

@Component
public class OrderProcessRoute extends RouteBuilder {
	public static final String MQ_ENDPOINT = "jms:queue:pizzashackOrderInbound?transacted=true&replyTo=pizzashackOrderOutbound"
			+ "&replyToType=Exclusive&requestTimeout=20s";

	public static final String ORDER_INTEGRATION_ENDPOINT = "direct:OrderProcessIntegration";

	@Resource
	private OrderProcessFormatTransformer orderProcessFormatTransformer;

	@Override
	public void configure() throws Exception {
		from(ORDER_INTEGRATION_ENDPOINT)
				.routeId(ORDER_INTEGRATION_ENDPOINT)
				.bean(orderProcessFormatTransformer, "orderRepXmlMarshal")
				// .setHeader("messageId", simple("${body.orderRequestId}"))
				.setExchangePattern(ExchangePattern.InOut)
				.to("log:myLog?showAll=true").to(MQ_ENDPOINT)
				.log("jmsMsgId set = ${in.header.JMSMessageId}")
				.to("log:myLog?showAll=true")
				.bean(orderProcessFormatTransformer, "orderRespXmlUnmarshal");
	}

}
