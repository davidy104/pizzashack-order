package co.nz.pizzashack.client.ds;

import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.utils.JerseyClientSupport;
import co.nz.pizzashack.client.utils.PizzashackJSONTransformer;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

@Service
public class PizzashackDSImpl extends JerseyClientSupport implements
		PizzashackDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackDSImpl.class);

	public final static String BASE_URL = "http://localhost:8111/rest/v1/";

	@Override
	public Set<PizzashackDto> pizzashackItems() throws Exception {
		LOGGER.info("pizzashackItems start:{} ");
		Set<PizzashackDto> pizzashackItems = null;
		WebResource webResource = client.resource(BASE_URL).path(
				"pizzashackList");

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = response.getClientResponseStatus();
		LOGGER.info("statusCode:{} ", statusCode);
		String respStr = this.getResponsePayload(response);
		if (statusCode.equals(Status.OK)) {
			pizzashackItems = PizzashackJSONTransformer
					.getAvailablePizzaList(respStr);
		} else {
			throw new Exception("pizzashackItems error: " + respStr);
		}
		LOGGER.info("pizzashackItems end:{} ");
		return pizzashackItems;
	}

	@Override
	public PizzashackDto getPizzashackDtoById(Long pizzashackId)
			throws Exception {
		LOGGER.info("getPizzashackDtoById start:{} ", pizzashackId);
		PizzashackDto pizzashackDto = null;
		WebResource webResource = client.resource(BASE_URL).path(
				"pizzashack/" + pizzashackId);
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = response.getClientResponseStatus();
		LOGGER.info("statusCode:{} ", statusCode);
		String respStr = this.getResponsePayload(response);
		if (statusCode.equals(Status.OK)) {
			pizzashackDto = PizzashackJSONTransformer.getPizzashack(respStr);
		} else {
			throw new Exception("getPizzashackDtoById error: " + respStr);
		}
		LOGGER.info("getPizzashackDtoById end:{} ");
		return pizzashackDto;
	}

}
