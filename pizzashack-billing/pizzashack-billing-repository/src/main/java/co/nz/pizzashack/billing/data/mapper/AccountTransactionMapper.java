package co.nz.pizzashack.billing.data.mapper;

import java.util.List;
import java.util.Map;

import co.nz.pizzashack.billing.data.AccountTransactionModel;

public interface AccountTransactionMapper {

	void saveAccountTrans(AccountTransactionModel accountTrans);

	void deleteAccountTrans(Long accountTransId);

	void updateAccountTrans(AccountTransactionModel accountTrans);

	List<AccountTransactionModel> getAccountTransactions(
			Map<String, Object> paramMap);

	AccountTransactionModel getAccountTransactionByTransNo(String accountTransNo);

}
