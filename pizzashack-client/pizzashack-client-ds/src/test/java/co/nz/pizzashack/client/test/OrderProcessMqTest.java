package co.nz.pizzashack.client.test;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.client.config.ApplicationConfiguration;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.integration.route.OrderProcessRoute;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
// @Ignore
public class OrderProcessMqTest {
	@Produce
	private ProducerTemplate producer;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessMqTest.class);

	@Test
	public void testOrderProcess() {
		OrderDto orderDto = OrderTestUtils.mockAutoPassOrder();
		LOGGER.info("order request:{} ", orderDto);
		orderDto = producer.requestBodyAndHeader(
				OrderProcessRoute.ORDER_INTEGRATION_ENDPOINT, orderDto,
				"messageId", orderDto.getOrderRequestId(), OrderDto.class);

		LOGGER.info("get result:{} ", orderDto);
	}

}
