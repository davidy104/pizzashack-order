package co.nz.pizzashack.client.ds;

import java.util.Set;

import co.nz.pizzashack.client.data.dto.BillingDto;
import co.nz.pizzashack.client.data.dto.BillingResp;
import co.nz.pizzashack.client.data.dto.OrderDto;

public interface OrderDS {

	OrderDto placeOrder(OrderDto order) throws Exception;

	OrderDto getOrderByNo(String orderNo) throws Exception;

	Set<OrderDto> getOrdersByCustomer(String customerEmail) throws Exception;

	BillingResp payForOrder(BillingDto billing) throws Exception;
}
