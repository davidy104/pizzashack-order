package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORDER_REQUEST_HISTORY")
public class OrderRequestHistoryModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MESSAGE_ID")
	private String messageId;

	@Column(name = "PROCESSOR_NAME")
	private String processorName;

	@Temporal(value = TemporalType.TIME)
	@Column(name = "created_at")
	private Date createAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
	private OrderModel order;

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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public static Builder getBuilder(String messageId, String processorName) {
		return new Builder(messageId, processorName);
	}

	public static class Builder {

		private OrderRequestHistoryModel built;

		public Builder(String messageId, String processorName) {
			built = new OrderRequestHistoryModel();
			built.messageId = messageId;
			built.processorName = processorName;

		}

		public OrderRequestHistoryModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("messageId", messageId)
				.append("processorName", processorName)
				.append("createAt", createAt).toString();
	}

}
