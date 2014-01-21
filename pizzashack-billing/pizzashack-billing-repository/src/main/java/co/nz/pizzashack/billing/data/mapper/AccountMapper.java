package co.nz.pizzashack.billing.data.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import co.nz.pizzashack.billing.data.AccountModel;

public interface AccountMapper {

	void saveAccount(AccountModel account);

	void deleteAccount(Long accountId);

	void updateAccount(AccountModel account);

	AccountModel getAccountById(@Param("accountId") Long accountId);

	List<AccountModel> getAccounts(Map<String, Object> paramMap);

	List<AccountModel> getAssociatedAccountTransactions(
			Map<String, Object> paramMap);
}
