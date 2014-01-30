package co.nz.pizzashack.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.ds.OrderProcessDS;

@Service
public class OrderDataEntryTestSupport {

	@Resource
	private OrderProcessDS orderProcessDs;

	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto doStartFlow(UserDto operator) throws Exception {
		return orderProcessDs.startOrderProcess(operator);
	}

	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto doDataEntry(String orderNo, OrderDto order,
			UserDto operator) throws Exception {
		return orderProcessDs.dataEntry(orderNo, order, operator);
	}
}
