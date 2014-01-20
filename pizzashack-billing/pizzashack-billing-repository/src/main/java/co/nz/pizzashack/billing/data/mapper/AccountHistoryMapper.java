package co.nz.pizzashack.billing.data.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import co.nz.pizzashack.billing.data.model.AccountHistoryModel;

public interface AccountHistoryMapper {

	void saveAccountHistory(AccountHistoryModel accountHistory);

	void deleteAccountHistory(Long accountHistoryId);

	void updateAccountHistory(AccountHistoryModel accountHistory);

	@Select("SELECT * FROM t_account_history WHERE account_hist_id = #{accountHistoryId}")
	AccountHistoryModel getAccountHistoryModelById(Long accountHistoryId);

	@Select("SELECT * FROM t_account_history WHERE account_trans_no = #{transNo}")
	AccountHistoryModel getAccountHistoryModelByTransNo(String transNo);

	List<AccountHistoryModel> getAccountHistories(Map<String, Object> paramMap);
}
