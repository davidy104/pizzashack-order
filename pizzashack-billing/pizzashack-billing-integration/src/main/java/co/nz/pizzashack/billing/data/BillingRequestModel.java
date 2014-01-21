package co.nz.pizzashack.billing.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("serial")
@Alias("billingRequest")
public class BillingRequestModel implements Serializable {

	private String messageId;
	private String processorName;
	private Date createdAt;
	private AccountTransactionModel accountTrans;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getProcessorName() {
		return processorName;
	}
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public AccountTransactionModel getAccountTrans() {
		return accountTrans;
	}
	public void setAccountTrans(AccountTransactionModel accountTrans) {
		this.accountTrans = accountTrans;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
