package co.nz.pizzashack.billing.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("account")
public class AccountModel implements Serializable {

	public enum AccountType {
		credit(0), debit(1);
		AccountType(int value) {
			this.value = value;
		}

		private final int value;

		public int value() {
			return value;
		}
	}

	private Long accountId;

	private String accountNo;

	private String securityNo;

	private BigDecimal balance = BigDecimal.ZERO;

	private Integer accountType;

	private Date createTime;

	private Date expireDate;

	private Set<AccountHistoryModel> histories;

	public void addAccountHistory(AccountHistoryModel accountHistory) {
		if (histories == null) {
			histories = new HashSet<AccountHistoryModel>();
		}
		histories.add(accountHistory);
	}

	public static Builder getBuilder(String accountNo, String securityNo,
			BigDecimal balance, Integer accountType, Date expireTime) {
		return new Builder(accountNo, securityNo, balance, accountType,
				expireTime);
	}

	public static Builder getBuilder(String accountNo, String securityNo,
			Integer accountType) {
		return new Builder(accountNo, securityNo, accountType);
	}

	public static class Builder {

		private AccountModel built;

		public Builder(String accountNo, String securityNo, BigDecimal balance,
				Integer accountType, Date expireDate) {
			built = new AccountModel();
			built.accountNo = accountNo;
			built.balance = balance;
			built.securityNo = securityNo;
			built.accountType = accountType;
			built.expireDate = expireDate;
		}

		public Builder(String accountNo, String securityNo, Integer accountType) {
			built = new AccountModel();
			built.accountNo = accountNo;
			built.securityNo = securityNo;
			built.accountType = accountType;
		}

		public AccountModel build() {
			return built;
		}
	}

	public String getSecurityNo() {
		return securityNo;
	}

	public void setSecurityNo(String securityNo) {
		this.securityNo = securityNo;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Set<AccountHistoryModel> getHistories() {
		return histories;
	}

	public void setHistories(Set<AccountHistoryModel> histories) {
		this.histories = histories;
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.accountNo, ((AccountModel) obj).accountNo)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.accountNo).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("accountId", accountId).append("accountNo", accountNo)
				.append("balance", balance).append("accountType", accountType)
				.append("createTime", createTime)
				.append("expireDate", expireDate).toString();
	}
}
