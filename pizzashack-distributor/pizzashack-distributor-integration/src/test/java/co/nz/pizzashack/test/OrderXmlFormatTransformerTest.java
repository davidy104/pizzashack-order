package co.nz.pizzashack.test;

import java.math.BigDecimal;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.nz.pizzashack.data.dto.OrderDetailsDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.integration.mapping.OrderProcessFormatTransformer;

@Ignore
public class OrderXmlFormatTransformerTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderXmlFormatTransformerTest.class);

	private OrderProcessFormatTransformer orderProcessFormatTransformer = new OrderProcessFormatTransformer();

	@Test
	public void testOrderReqXmlUnmarshal() {

		String xmlStr = "<order><address>25 mavon downs</address><order-list><order-details><pizza-name>Chicken Parmesan</pizza-name><quantity>2</quantity></order-details>"
				+ "<order-details><pizza-name>Spicy Italian</pizza-name><quantity>4</quantity></order-details></order-list>"
				+ "<customer><name>david</name><email>david.yuan124@gmail.com</email></customer></order>";
		OrderDto orderDto = orderProcessFormatTransformer
				.orderReqXmlUnmarshal(xmlStr);
		LOGGER.info("orderDto:{} ", orderDto);
	}

	@Test
	public void testOrderRespXmlMarshal() {
		OrderProcessDto orderProcessDto = new OrderProcessDto();

		ProcessActivityDto pendingActivity = new ProcessActivityDto();
		pendingActivity.setAssignee("david");
		pendingActivity.setName("orderReview");
		pendingActivity.setType("userTask");
		orderProcessDto.setPendingActivity(pendingActivity);

		UserDto operator = new UserDto();
		operator.setUsername("brad");
		orderProcessDto.setOperator(operator);

		orderProcessDto.setOrderProcessId(1L);
		orderProcessDto.setCreateTime("2013-10-10 13:21:34");
		orderProcessDto.setCompleteTime("2014-10-10 13:21:34");

		OrderDto order = new OrderDto();
		order.setOrderNo("390293029302");
		order.setAddress("25 marvon downs");
		order.setOrderTime("2013-10-10 13:21:34");
		order.setQty(5);
		order.setStatus("billing");
		order.setTotalPrice(new BigDecimal(100.00));

		OrderDetailsDto orderDetails = new OrderDetailsDto();
		orderDetails.setPizzaName("abc");
		orderDetails.setQty(1);
		orderDetails.setTotalPrice(new BigDecimal(24.00));
		order.addPizzaOrder(orderDetails);

		orderDetails = new OrderDetailsDto();
		orderDetails.setPizzaName("erer");
		orderDetails.setQty(2);
		orderDetails.setTotalPrice(new BigDecimal(64.00));
		order.addPizzaOrder(orderDetails);

		orderProcessDto.setOrder(order);

		String xmlStr = orderProcessFormatTransformer
				.orderRespXmlMarshal(orderProcessDto);

		LOGGER.info("xmlStr:{} ", xmlStr);
	}
}
