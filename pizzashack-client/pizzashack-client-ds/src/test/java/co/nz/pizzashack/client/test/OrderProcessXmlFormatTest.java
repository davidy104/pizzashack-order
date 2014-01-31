package co.nz.pizzashack.client.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.integration.mapping.OrderProcessFormatTransformer;

public class OrderProcessXmlFormatTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessXmlFormatTest.class);
	OrderProcessFormatTransformer orderProcessFormatTransformer = new OrderProcessFormatTransformer();

	@Test
	public void test() {
		String xmlStr = "<order-process><process-id>4</process-id><create-time>1970-01-01 09:18:35</create-time><complete-time>null</complete-time><operator>general</operator>"
				+ "<order><address>25 mavon downs</address><quantity>6</quantity><total-price>162.00</total-price> <status>pendingOnBilling</status><order-time>2014-01-31 08:18:35</order-time>"
				+ "<deliver-time>null</deliver-time><order-list><order-details><pizza-name>Chicken Parmesan</pizza-name><quantity>2</quantity><total-price>50.00</total-price></order-details>"
				+ "<order-details><pizza-name>Spicy Italian</pizza-name><quantity>4</quantity><total-price>112.00</total-price> </order-details> </order-list> </order><customer><name>david</name>"
				+ "<email>david.yuan124@gmail.com</email></customer><pending-activity><name>Billing fill in</name><type>userTask</type><assignee>4</assignee></pending-activity></order-process>";

		OrderDto order = orderProcessFormatTransformer
				.orderRespXmlUnmarshal(xmlStr);
		LOGGER.info("order:{}", order);
	}

}
