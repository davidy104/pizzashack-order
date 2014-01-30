package co.nz.pizzashack.integration.processor;

import static co.nz.pizzashack.data.predicates.UserPredicates.findByUsername;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.converter.OrderConverter;
import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderRequestHistoryModel;
import co.nz.pizzashack.data.model.UserModel;
import co.nz.pizzashack.data.repository.OrderRequestHistoryRepository;
import co.nz.pizzashack.data.repository.UserRepository;
import co.nz.pizzashack.ds.OrderProcessDS;

@Component
public class OrderRequestProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderRequestProcessor.class);

	@Resource
	private OrderProcessDS orderProcessDs;

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserConverter userConverter;

	@Resource
	private OrderRequestHistoryRepository orderRequestHistoryRepository;

	@Resource
	private OrderConverter orderConverter;

	private static final String DEFAULT_OPERATOR = "general";

	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.info("orderRequestProcessor start:{} ");
		OrderDto orderDto = exchange.getIn().getBody(OrderDto.class);
		String messageId = (String) exchange.getIn().getHeader("messageId");
		LOGGER.info("order request:{} ", orderDto);
		LOGGER.info("messageId:{} ", messageId);

		UserModel userModel = userRepository
				.findOne(findByUsername(DEFAULT_OPERATOR));
		UserDto operator = userConverter.toDto(userModel);

		LOGGER.info("default operator:{} ", operator);

		OrderProcessDto orderProcessDto = orderProcessDs
				.startOrderProcess(operator);
		LOGGER.info("after start process:{} ", orderProcessDto);
		String orderNo = orderProcessDto.getOrder().getOrderNo();
		orderProcessDto = orderProcessDs.dataEntry(orderNo, orderDto, operator);
		LOGGER.info("after dataEntry:{} ", orderProcessDto);

		OrderModel orderModel = orderConverter.toModel(orderProcessDto
				.getOrder());

		OrderRequestHistoryModel orderRequestHistory = orderRequestHistoryRepository
				.findOne(messageId);
		orderRequestHistory.setOrder(orderModel);

		exchange.getIn().setBody(orderProcessDto, OrderProcessDto.class);
		LOGGER.info("orderRequestProcessor end:{} ");
	}

}
