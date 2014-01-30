package co.nz.pizzashack.integration.route;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.integration.mapping.OrderProcessFormatTransformer;
import co.nz.pizzashack.integration.processor.OrderDuplicatedReqProcessor;
import co.nz.pizzashack.integration.processor.OrderRequestProcessor;

@Component("orderProcessRoute")
public class OrderProcessRoute extends RouteBuilder {

	public static final String ENDPOINT = "jms:queue:pizzashackOrderInbound?transacted=true";
	public static final String OUTBOUND_END_POINT = "jms:queue:pizzashackOrderOutbound?jmsMessageType=Text";
	public static final String ROUTEID = "orderProcessRoute";

	public static final String ORDER_DUPLICATED_ENDPOINT = "direct:orderDuplicatedProcess";
	public static final String ORDER_REQULAR_ENDPOINT = "direct:orderRegularProcess";

	@Resource
	private JdbcMessageIdRepository orderJdbcMessageIdRepository;

	@Resource
	private OrderProcessFormatTransformer orderProcessFormatTransformer;

	@Resource
	private OrderRequestProcessor orderRequestProcessor;

	@Resource
	private OrderDuplicatedReqProcessor orderDuplicatedReqProcessor;

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
						orderJdbcMessageIdRepository).skipDuplicate(false)
				.choice().when(property(Exchange.DUPLICATE_MESSAGE))
				.to(ORDER_DUPLICATED_ENDPOINT).otherwise()
				.to(ORDER_REQULAR_ENDPOINT).endChoice()
				.log("Signalled to stop route");

		from(ORDER_DUPLICATED_ENDPOINT)
				.routeId(ORDER_DUPLICATED_ENDPOINT)
				.to("log:co.nz.pizzashack.integration.route.OrderProcessRoute?showAll=true&level=INFO")
				.process(orderDuplicatedReqProcessor)
				.bean(orderProcessFormatTransformer, "orderRespXmlMarshal")
				.log("after marshal ${body}")
				.setHeader("duplicatedOrderRequest", constant("yes"))
				.to(OUTBOUND_END_POINT);

		from(ORDER_REQULAR_ENDPOINT).routeId(ORDER_REQULAR_ENDPOINT)
				.bean(orderProcessFormatTransformer, "orderReqXmlUnmarshal")
				.process(orderRequestProcessor)
				.bean(orderProcessFormatTransformer, "orderRespXmlMarshal")
				.to("log:output").to(OUTBOUND_END_POINT);
	}

}
