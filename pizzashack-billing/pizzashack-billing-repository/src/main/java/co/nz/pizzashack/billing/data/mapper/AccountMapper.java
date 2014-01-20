package co.nz.pizzashack.billing.data.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import co.nz.pizzashack.billing.data.model.AccountModel;

public interface AccountMapper {

	void saveAccount(AccountModel account);

	void deleteAccount(Long accountId);

	void updateAccount(AccountModel account);

	@Select("SELECT * FROM T_ACCOUNT WHERE ACCOUNT_ID = #{accountId}")
	AccountModel getAccountById(@Param("accountId") Long accountId);

	@Select("SELECT * FROM T_ACCOUNT WHERE ACCOUNT_NO = #{accountNo} and SECURITY_NO=#{securityNo}")
	AccountModel getAccountByAccountNoAndSecurityNo(@Param("accountNo") String accountNo,@Param("securityNo") String securityNo);

	List<AccountModel> getAccounts(Map<String, Object> paramMap);
}
