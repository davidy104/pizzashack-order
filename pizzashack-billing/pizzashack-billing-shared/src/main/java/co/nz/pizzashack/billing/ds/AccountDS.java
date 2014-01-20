package co.nz.pizzashack.billing.ds;

import java.math.BigDecimal;

import co.nz.pizzashack.billing.data.dto.AccountAuthenticationDto;
import co.nz.pizzashack.billing.data.dto.AccountDto;

public interface AccountDS {

	AccountDto deduct(AccountDto account, BigDecimal deductAmount)
			throws Exception;

	AccountAuthenticationDto accountAuthentication(AccountDto account);

}
