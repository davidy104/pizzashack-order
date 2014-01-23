package co.nz.pizzashack.integration.api;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.utils.GeneralUtils;

public class PizzashackAPIUtils {
	public static final String UNKNOWN_ERROR_CODE = "Unknown error";

	public static final String BAD_REQUEST_CODE = "Request is incorrect";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackAPIUtils.class);

	public static GenericAPIError errorHandle(Exception e) {
		GenericAPIError result = null;
		Response.Status respStatus = Response.Status.INTERNAL_SERVER_ERROR;
		StringBuilder errorMsgBuffer = new StringBuilder();
		String errorMsg = e.getMessage() == null ? UNKNOWN_ERROR_CODE : e
				.getMessage();
		String errorCauseStr = GeneralUtils.getExceptionInfo(e);
		errorMsgBuffer.append(errorMsg).append(":").append(errorCauseStr);

		if (e instanceof NotFoundException) {
			LOGGER.debug("get NotFoundException");
			respStatus = Response.Status.NOT_FOUND;
		}
		result = GenericAPIError.getBuilder(respStatus,
				errorMsgBuffer.toString()).build();
		return result;
	}

	public static Response buildResponse(Object entity,
			GenericAPIError genericAPIError) {
		Response response = null;
		Response.Status respStatus = null;
		String errorMessage = null;

		if (genericAPIError != null) {
			respStatus = genericAPIError.getRespStatus();
			errorMessage = genericAPIError.getErrorMessage();
		}
		respStatus = respStatus == null ? Status.OK : respStatus;
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);

		if (respStatus != Status.OK) {
			errorMessage = errorMessage == null
					? UNKNOWN_ERROR_CODE
					: errorMessage;
			response = Response.status(respStatus).cacheControl(cc)
					.entity(errorMessage).build();
		} else {
			response = Response.status(respStatus).cacheControl(cc)
					.entity(entity).build();
		}

		return response;
	}
}
