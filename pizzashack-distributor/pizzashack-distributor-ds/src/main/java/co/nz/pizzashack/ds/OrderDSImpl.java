package co.nz.pizzashack.ds;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.data.dto.OrderDto;
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

	@Override
	public OrderDto getOrderByOrderNo(String orderNo) throws Exception {

		return null;
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
