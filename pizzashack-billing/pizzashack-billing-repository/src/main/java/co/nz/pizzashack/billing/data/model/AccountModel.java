package co.nz.pizzashack.billing.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ACCOUNT")
public class AccountModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID", insertable = false, updatable = false)
	private Long accountId;

	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	@Column(name = "BALANCE")
	private BigDecimal balance = BigDecimal.ZERO;

	@Column(name = "ACCOUNT_TYPE")
	private Integer accountType;

	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIME)
	private Date createTime;

	@Column(name = "EXPIRE_DATE")
	@Temporal(TemporalType.DATE)
	private Date expireDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST }, mappedBy = "account")
	private Set<AccountHistoryModel> histories;

	public void addAccountHistory(AccountHistoryModel accountHistory) {
		if (histories == null) {
			histories = new HashSet<AccountHistoryModel>();
		}
		histories.add(accountHistory);
	}

	public static Builder getBuilder(String accountNo, BigDecimal balance,
			Integer accountType, Date expireTime) {
		return new Builder(accountNo, balance, accountType, expireTime);
	}

	public static Builder getBuilder(String accountNo, Integer accountType) {
		return new Builder(accountNo, accountType);
	}

	public static class Builder {

		private AccountModel built;

		public Builder(String accountNo, BigDecimal balance,
				Integer accountType, Date expireDate) {
			built = new AccountModel();
			built.accountNo = accountNo;
			built.balance = balance;
			built.accountType = accountType;
			built.expireDate = expireDate;
		}

		public Builder(String accountNo, Integer accountType) {
			built = new AccountModel();
			built.accountNo = accountNo;
			built.accountType = accountType;
		}

		public AccountModel build() {
			return built;
		}
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
