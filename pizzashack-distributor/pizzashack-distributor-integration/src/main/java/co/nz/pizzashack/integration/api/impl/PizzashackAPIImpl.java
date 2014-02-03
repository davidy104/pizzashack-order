package co.nz.pizzashack.integration.api.impl;

import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.PizzashackDto;
import co.nz.pizzashack.ds.OrderDS;
import co.nz.pizzashack.ds.OrderProcessQueryDS;
import co.nz.pizzashack.ds.PizzashackDS;
import co.nz.pizzashack.integration.api.GenericAPIError;
import co.nz.pizzashack.integration.api.PizzashackAPI;
import co.nz.pizzashack.integration.api.PizzashackAPIUtils;

@Component
@Path("/v1")
public class PizzashackAPIImpl implements PizzashackAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackAPIImpl.class);

	@Resource
	private PizzashackDS pizzashackDs;

	@Resource
	private OrderDS orderDs;

	@Resource
	private OrderProcessQueryDS orderProcessQueryDs;

	@Override
	@Path("/pizzashackList")
	@GET
	@Produces("application/json")
	public Response getPizzashacks() {
		LOGGER.info("getPizzashacks start:{}");
		Set<PizzashackDto> dtoSet = null;
		GenericAPIError genericAPIError = null;
		try {
			dtoSet = pizzashackDs.getAllItems();
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.info("getPizzashacks end:{}");
		return PizzashackAPIUtils.buildResponse(dtoSet, genericAPIError);
	}

	@Override
	@Path("/pizzashack/{pizzashackId}")
	@GET
	@Produces("application/json")
	public Response getPizzashackById(
			@PathParam("pizzashackId") Long pizzashackId) {
		LOGGER.info("getPizzashackById start:{}");
		GenericAPIError genericAPIError = null;
		PizzashackDto pizzashack = null;
		try {
			pizzashack = pizzashackDs.getPizzashackById(pizzashackId);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.info("getPizzashackById end:{}");
		return PizzashackAPIUtils.buildResponse(pizzashack, genericAPIError);
	}

	@Override
	@Path("/customerOrderList/{custEmail}")
	@GET
	@Produces("application/json")
	public Response getOrdersforCustomer(
			@PathParam("custEmail") String customerEmail) {
		LOGGER.info("getOrdersforCustomer start:{}", customerEmail);
		GenericAPIError genericAPIError = null;
		Set<OrderDto> orders = null;
		try {
			orders = orderDs.getOrderByCustomer(customerEmail);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.info("getOrdersforCustomer end:{}");
		return PizzashackAPIUtils.buildResponse(orders, genericAPIError);
	}

	@Override
	@Path("/orderProcess/search")
	@GET
	@Produces("application/json")
	public Response getOrderProcessesByCustomer(
			@QueryParam("customerEmail") String customerEmail) {
		LOGGER.info("getOrderProcessesByCustomer start:{}", customerEmail);
		Set<OrderProcessDto> orderProcesses = null;
		GenericAPIError genericAPIError = null;
		try {
			orderProcesses = orderProcessQueryDs
					.getOrderProcessesByCustomer(customerEmail);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.info("getOrderProcessesByCustomer end:{}");
		return PizzashackAPIUtils
				.buildResponse(orderProcesses, genericAPIError);
	}

	@Override
	@Path("/orderProcess/{orderNo}")
	@GET
	@Produces("application/json")
	public Response getOrderProcessByOrderNo(String orderNo) {
		LOGGER.info("getOrderProcessByOrderNo start:{}", orderNo);
		GenericAPIError genericAPIError = null;
		OrderProcessDto orderProcessDto = null;

		try {
			orderProcessDto = orderProcessQueryDs
					.getOrderProcessByOrderNo(orderNo);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.info("getOrderProcessByOrderNo end:{}");
		return PizzashackAPIUtils.buildResponse(orderProcessDto,
				genericAPIError);
	}

	@Override
	@Path("/order/{orderNo}")
	@GET
	@Produces("application/json")
	public Response getOrderByOrderNo(@PathParam("orderNo") String orderNo) {
		LOGGER.info("getOrderByNo start:{}", orderNo);
		GenericAPIError genericAPIError = null;
		OrderDto order = null;
		try {
			order = orderDs.getOrderByOrderNo(orderNo);
		} catch (Exception e) {
			genericAPIError = PizzashackAPIUtils.errorHandle(e);
		}
		LOGGER.info("getOrderByNo end:{}");
		return PizzashackAPIUtils.buildResponse(order, genericAPIError);
	}

}
