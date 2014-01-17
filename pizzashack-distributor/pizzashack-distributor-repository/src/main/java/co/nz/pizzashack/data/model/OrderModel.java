package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "T_ORDER")
public class OrderModel implements Serializable {

	public enum OrderStatus {
		dataEntry(0), pendingOnReview(3), pendingOnDeliver(5), delivered(7), rejected(
				9);
		OrderStatus(int value) {
			this.value = value;
		}

		private final int value;

		public int value() {
			return value;
		}
	}

	public enum PaymentStatus {
		payied(1), unpay(0);
		PaymentStatus(int value) {
			this.value = value;
		}

		private final int value;

		public int value() {
			return value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID", insertable = false, updatable = false)
	private Long orderId;

	@Column(name = "ORDER_NO")
	private String orderNo;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUST_ID", referencedColumnName = "CUST_ID")
	private CustomerModel customer;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "PAYMENT_STATUS")
	private Integer paymentStatus;

	@Column(name = "TOTAL_PRICE")
	private BigDecimal totalPrice;

	@Column(name = "ORDER_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;

	@Column(name = "DELIVER_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliverTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "orderPizzashackPK.order")
	private List<OrderPizzashackModel> orderPizzashackModels;

	public void addOrderPizzashack(OrderPizzashackModel orderPizzashack) {
		if (orderPizzashackModels == null) {
			orderPizzashackModels = new ArrayList<OrderPizzashackModel>();
		}
		orderPizzashackModels.add(orderPizzashack);
	}

	public List<OrderPizzashackModel> getOrderPizzashackModels() {
		return orderPizzashackModels;
	}

	public void setOrderPizzashackModels(
			List<OrderPizzashackModel> orderPizzashackModels) {
		this.orderPizzashackModels = orderPizzashackModels;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public static Builder getBuilder(CustomerModel customer) {
		return new Builder(customer);
	}

	public static class Builder {

		private OrderModel built;

		public Builder(CustomerModel customer) {
			built = new OrderModel();
			built.customer = customer;
		}

		public OrderModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.orderNo, ((OrderModel) obj).orderNo)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.orderNo).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderId", orderId).append("orderNo", orderNo)
				.append("quantity", quantity).append("status", status)
				.append("paymentStatus", paymentStatus)
				.append("deliverTime", deliverTime)
				.append("totalPrice", totalPrice)
				.append("orderTime", orderTime).toString();
	}

}
