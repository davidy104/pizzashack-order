package co.nz.pizzashack.client.ds;

import java.util.Set;

import co.nz.pizzashack.client.data.dto.AccountDto;
import co.nz.pizzashack.client.data.dto.OrderDto;

public interface OrderDS {

	OrderDto placeOrder(OrderDto order) throws Exception;

	OrderDto getOrderByNo(String orderNo) throws Exception;

	Set<OrderDto> getOrdersByCustomer(String customerEmail) throws Exception;

	OrderDto payForOrder(String orderNo, AccountDto account) throws Exception;
}
