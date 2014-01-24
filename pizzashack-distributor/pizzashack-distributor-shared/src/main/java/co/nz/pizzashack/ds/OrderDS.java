package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.OrderDto;

public interface OrderDS {

	OrderDto getOrderByOrderNo(String orderNo) throws Exception;

	Set<OrderDto> getOrderByCustomer(String customerEmail) throws Exception;

	void apCaculate(OrderDto order) throws Exception;

}
