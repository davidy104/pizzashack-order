package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.OrderPredicates.findByOrderNo;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.OrderLoadStrategies;
import co.nz.pizzashack.data.converter.OrderConverter;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.support.PizzashackCaculator;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class OrderDSImpl implements OrderDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderDSImpl.class);

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private PizzashackCaculator pizzashackCaculator;

	@Resource
	private OrderConverter orderConverter;

	@Override
	public OrderDto getOrderByOrderNo(String orderNo) throws Exception {
		LOGGER.info("getOrderByOrderNo start:{} ", orderNo);
		OrderDto found = null;
		OrderModel orderModel = orderRepository.findOne(findByOrderNo(orderNo));
		if (orderModel == null) {
			throw new NotFoundException("Order not found by no[" + orderNo
					+ "]");
		}
		found = orderConverter.toDto(orderModel, OrderLoadStrategies.LOAD_ALL);
		LOGGER.info("getOrderByOrderNo end:{} ", found);
		return found;
	}

	@Override
	public Set<OrderDto> getOrderByCustomer(String customerEmail)
			throws Exception {

		return null;
	}

	@Override
	public void apCaculate(OrderDto order) throws Exception {
		pizzashackCaculator.apCaculate(order);
	}

}
