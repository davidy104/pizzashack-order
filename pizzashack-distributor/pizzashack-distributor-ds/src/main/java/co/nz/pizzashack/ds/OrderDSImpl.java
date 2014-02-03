package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.OrderPredicates.findByCustomerEmail;
import static co.nz.pizzashack.data.predicates.OrderPredicates.findByOrderNo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.IteratorUtils;
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

	@SuppressWarnings("unchecked")
	@Override
	public Set<OrderDto> getOrderByCustomer(String customerEmail)
			throws Exception {
		LOGGER.info("getOrderByCustomer start:{} ", customerEmail);
		Set<OrderDto> orders = null;
		Iterable<OrderModel> iterable = orderRepository
				.findAll(findByCustomerEmail(customerEmail));
		List<OrderModel> resultList = IteratorUtils.toList(iterable.iterator());
		if (resultList != null && resultList.size() > 0) {
			orders = new HashSet<OrderDto>();
			for (OrderModel model : resultList) {
				orders.add(orderConverter.toDto(model,
						OrderLoadStrategies.LOAD_CUSTOMER));
			}
		}
		LOGGER.info("getOrderByCustomer end:{} ");
		return orders;
	}

	@Override
	public void apCaculate(OrderDto order) throws Exception {
		pizzashackCaculator.apCaculate(order);
	}

}
