package co.nz.pizzashack.billing.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("accountHistory")
public class AccountHistoryModel implements Serializable {

	public enum TransType {
		in(0), out(1);
		TransType(int value) {
			this.value = value;
		}

		private final int value;

		public int value() {
			return value;
		}
	}

	private Long accountHistoryId;
	private String accountTransNo;
	private AccountModel account;
	private BigDecimal transAmount;
	private Date createTime;
	private Integer transType;

	public Long getAccountHistoryId() {
		return accountHistoryId;
	}

	public void setAccountHistoryId(Long accountHistoryId) {
		this.accountHistoryId = accountHistoryId;
	}

	public String getAccountTransNo() {
		return accountTransNo;
	}

	public void setAccountTransNo(String accountTransNo) {
		this.accountTransNo = accountTransNo;
	}

	public AccountModel getAccount() {
		return account;
	}

	public void setAccount(AccountModel account) {
		this.account = account;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public static Builder getBuilder(String accountTransNo,
			AccountModel account, Integer transType, BigDecimal transAmount) {
		return new Builder(accountTransNo, account, transType, transAmount);
	}

	public static class Builder {

		private AccountHistoryModel built;

		public Builder(String accountTransNo, AccountModel account,
				Integer transType, BigDecimal transAmount) {
			built = new AccountHistoryModel();
			built.accountTransNo = accountTransNo;
			built.account = account;
			built.transType = transType;
			built.transAmount = transAmount;
		}

		public AccountHistoryModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.accountTransNo,
				((AccountHistoryModel) obj).accountTransNo).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.accountTransNo).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("accountHistoryId", accountHistoryId)
				.append("accountTransNo", accountTransNo)
				.append("transAmount", transAmount)
				.append("createTime", createTime)
				.append("transType", transType).toString();
	}
}
