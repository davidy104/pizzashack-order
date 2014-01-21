package co.nz.pizzashack.billing.integration.ws;

import java.util.Set;

import javax.jws.WebParam;
import javax.jws.WebService;

import co.nz.pizzashack.billing.data.dto.AccountDto;
import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto;
import co.nz.pizzashack.billing.data.dto.BillingTransactionDto;

@WebService
public interface BillingWebServices {
	AccountTransactionRespDto accountAuthentication(@WebParam AccountDto account)
			throws FaultMessage;;

	Set<BillingTransactionDto> getAllTransactionsForAccount(
			@WebParam AccountDto account) throws FaultMessage;;
}
