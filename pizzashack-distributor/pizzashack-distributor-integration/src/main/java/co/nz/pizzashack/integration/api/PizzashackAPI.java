package co.nz.pizzashack.integration.api;

import javax.ws.rs.core.Response;

public interface PizzashackAPI {

	Response getPizzashacks();

	Response getOrdersforCustomer(String customerEmail);

	Response getOrderByNo(String orderNo);
}
