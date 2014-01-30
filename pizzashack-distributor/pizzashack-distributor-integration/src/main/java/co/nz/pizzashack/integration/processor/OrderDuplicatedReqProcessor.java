package co.nz.pizzashack.integration.processor;

import static co.nz.pizzashack.data.predicates.OrderProcessPredicates.findByOrderNo;

import javax.annotation.Resource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.converter.OrderProcessConverter;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.model.OrderRequestHistoryModel;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRequestHistoryRepository;

@Component
public class OrderDuplicatedReqProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderDuplicatedReqProcessor.class);

	@Resource
	private OrderRequestHistoryRepository orderRequestHistoryRepository;

	@Resource
	private OrderProcessRepository orderProcessRepository;

	@Resource
	private OrderProcessConverter orderProcessConverter;

	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.info("orderDuplicatedReqProcessor start:{} ");
		String messageId = (String) exchange.getIn().getHeader("messageId");
		LOGGER.info("messageId:{} ", messageId);
		OrderRequestHistoryModel orderRequestHistory = orderRequestHistoryRepository
				.findOne(messageId);
		OrderModel orderModel = orderRequestHistory.getOrder();

		OrderProcessModel orderProcessModel = orderProcessRepository
				.findOne(findByOrderNo(orderModel.getOrderNo()));
		LOGGER.info("orderProcessModel:{} ", orderProcessModel);
		OrderProcessDto orderProcessDto = orderProcessConverter
				.toDto(orderProcessModel);
		exchange.getIn().setBody(orderProcessDto, OrderProcessDto.class);
		LOGGER.info("orderDuplicatedReqProcessor end:{} ");
	}

}
