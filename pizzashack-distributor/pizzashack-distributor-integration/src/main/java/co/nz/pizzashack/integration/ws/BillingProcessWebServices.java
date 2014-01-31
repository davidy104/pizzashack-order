package co.nz.pizzashack.integration.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import co.nz.pizzashack.data.dto.BillingDto;

@WebService
public interface BillingProcessWebServices {
	BillingResponse billingProcess(@WebParam BillingDto billingReq)
			throws FaultMessage;
}
