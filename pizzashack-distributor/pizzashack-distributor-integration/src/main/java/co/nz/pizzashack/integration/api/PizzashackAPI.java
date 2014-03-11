package co.nz.pizzashack.integration.api;

import javax.ws.rs.core.Response;

public interface PizzashackAPI {

	Response getPizzashacks();

	Response getPizzashackById(Long pizzashackId);

	Response getPizzashackByName(String pizzashackName);

	Response getOrderProcessesByCustomer(String customerEmail);

	Response getOrderProcessByOrderNo(String orderNo);

	Response getOrdersforCustomer(String customerEmail);

	Response getOrderByOrderNo(String orderNo);

}
