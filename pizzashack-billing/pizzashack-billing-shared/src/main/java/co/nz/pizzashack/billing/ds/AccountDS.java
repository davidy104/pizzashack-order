package co.nz.pizzashack.billing.ds;

import java.math.BigDecimal;
import java.util.Set;

import co.nz.pizzashack.billing.data.dto.AccountAuthenticationDto;
import co.nz.pizzashack.billing.data.dto.AccountDto;
import co.nz.pizzashack.billing.data.dto.AccountHistoryDto;

public interface AccountDS {

	void createAccount(AccountDto account) throws Exception;

	AccountDto getAccountByAccountNo(String accountNo, String securityNo) throws Exception;

	void deleteAccount(Long accountId) throws Exception;

	AccountAuthenticationDto deduct(AccountDto account, BigDecimal deductAmount)
			throws Exception;

	AccountAuthenticationDto accountAuthentication(AccountDto account);

	Set<AccountHistoryDto> getAllHistoryForAccount(String accountNo,
			String securityNo, Integer accountType) throws Exception;

}
