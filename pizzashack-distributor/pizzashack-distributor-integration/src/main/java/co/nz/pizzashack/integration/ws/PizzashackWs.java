package co.nz.pizzashack.integration.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import co.nz.pizzashack.data.dto.PizzashackDto;

@WebService
public interface PizzashackWs {
	PizzashackDto getPizzashackByName(@WebParam String pizzashackName)
			throws FaultMessage;
}
