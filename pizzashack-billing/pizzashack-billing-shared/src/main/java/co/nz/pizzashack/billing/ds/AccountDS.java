package co.nz.pizzashack.billing.ds;

import java.util.Set;

import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto;
import co.nz.pizzashack.billing.data.dto.AccountDto;
import co.nz.pizzashack.billing.data.dto.BillingTransactionDto;

public interface AccountDS {

	Long createAccount(AccountDto account) throws Exception;

	AccountDto getAccountById(Long accountId) throws Exception;

	AccountDto getAccountByAccountNo(String accountNo, String securityNo)
			throws Exception;

	void deleteAccount(Long accountId) throws Exception;

	AccountTransactionRespDto deduct(BillingTransactionDto billingTrans)
			throws Exception;

	AccountTransactionRespDto accountAuthentication(AccountDto account);

	BillingTransactionDto getBillingTransactionByTransNo(String transNo)
			throws Exception;

	Set<BillingTransactionDto> getAllTransactionsForAccount(String accountNo,
			String securityNo, Integer accountType) throws Exception;

}
