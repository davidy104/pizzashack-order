package co.nz.pizzashack.api.impl;

import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.api.GenericAPIError;
import co.nz.pizzashack.api.PizzashackAPI;
import co.nz.pizzashack.api.PizzashackAPIUtils;
import co.nz.pizzashack.data.dto.PizzashackDto;
import co.nz.pizzashack.ds.PizzashackDS;

@Component
@Path("/v1")
public class PizzashackAPIImpl implements PizzashackAPI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackAPIImpl.class);

	@Resource
	private PizzashackDS pizzashackDs;

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
	public Response getOrdersforCustomer(String customerEmail) {

		return null;
	}

	@Override
	public Response getOrderByNo(String orderNo) {

		return null;
	}

}
