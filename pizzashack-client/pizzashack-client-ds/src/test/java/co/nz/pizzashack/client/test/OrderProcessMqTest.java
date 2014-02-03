package co.nz.pizzashack.client.test;

import java.math.BigDecimal;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.client.config.ApplicationConfiguration;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.integration.route.OrderProcessRoute;
import co.nz.pizzashack.client.integration.ws.client.stub.BillingDto;
import co.nz.pizzashack.client.integration.ws.client.stub.BillingResponse;
import co.nz.pizzashack.client.utils.GeneralUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
// @Ignore
public class OrderProcessMqTest {
	@Produce
	private ProducerTemplate producer;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessMqTest.class);

	@Test
	public void testOrderProcessAutoPassed() {
		String requestId = String.valueOf(GeneralUtils.getRandomNumber(5));
		OrderDto orderDto = OrderTestUtils.mockAutoPassOrder();
		LOGGER.info("order request:{} ", orderDto);
		orderDto = producer.requestBodyAndHeader(
				OrderProcessRoute.ORDER_INTEGRATION_ENDPOINT, orderDto,
				"messageId", requestId, OrderDto.class);
		LOGGER.info("get result after dataEntry:{} ", orderDto);

		String orderNo = orderDto.getOrderNo();
		BigDecimal totalPrice = orderDto.getTotalPrice();
		BillingDto billingDto = OrderTestUtils.mockBilling(orderNo,
				String.valueOf(totalPrice));
		LOGGER.info("billingDto:{} ", billingDto);
		BillingResponse billingResponse = producer.requestBody(
				"cxf:bean:billingProcessEndpoint?dataFormat=POJO", billingDto,
				BillingResponse.class);

		LOGGER.info("billingResponse:{} ", billingResponse);
	}

	@Test
	public void testOrderProcessManualUWRequired() {
		String requestId = String.valueOf(GeneralUtils.getRandomNumber(5));
		OrderDto orderDto = OrderTestUtils.mockManualUWOrder();
		LOGGER.info("order request:{} ", orderDto);
		orderDto = producer.requestBodyAndHeader(
				OrderProcessRoute.ORDER_INTEGRATION_ENDPOINT, orderDto,
				"messageId", requestId, OrderDto.class);
		LOGGER.info("get result after dataEntry:{} ", orderDto);

	}

}
