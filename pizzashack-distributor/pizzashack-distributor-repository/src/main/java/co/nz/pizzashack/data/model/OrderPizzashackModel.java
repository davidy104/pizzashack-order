package co.nz.pizzashack.data.model;

import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "T_ORDER_PIZZASHACK")
@AssociationOverrides({
		@AssociationOverride(name = "orderPizzashackPK.order", joinColumns = @JoinColumn(name = "ORDER_ID")),
		@AssociationOverride(name = "orderPizzashackPK.pizzashack", joinColumns = @JoinColumn(name = "PIZZASHACK_ID")) })
public class OrderPizzashackModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_PIZZA_ID", insertable = false, updatable = false)
	private Long orderPizzashackId;

	@Embedded
	private OrderPizzashackPK orderPizzashackPK;

	@Column(name = "QTY")
	private Integer qty;

	@Column(name = "TOTAL_PRICE")
	private BigDecimal totalPrice;

	public Long getOrderPizzashackId() {
		return orderPizzashackId;
	}

	public void setOrderPizzashackId(Long orderPizzashackId) {
		this.orderPizzashackId = orderPizzashackId;
	}

	public OrderPizzashackPK getOrderPizzashackPK() {
		return orderPizzashackPK;
	}

	public void setOrderPizzashackPK(OrderPizzashackPK orderPizzashackPK) {
		this.orderPizzashackPK = orderPizzashackPK;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Transient
	public OrderModel getOrderModel() {
		return getOrderPizzashackPK().getOrder();
	}

	public void setOrderModel(OrderModel order) {
		getOrderPizzashackPK().setOrder(order);
	}

	@Transient
	public PizzashackModel getPizzashackModel() {
		return getOrderPizzashackPK().getPizzashack();
	}

	public void setPizzashackModel(PizzashackModel pizzashack) {
		getOrderPizzashackPK().setPizzashack(pizzashack);
	}

	public static Builder getBuilder(OrderModel order,
			PizzashackModel pizzashack, Integer qty) {
		return new Builder(order, pizzashack, qty);
	}

	public static Builder getBuilder(OrderModel order,
			PizzashackModel pizzashack, Integer qty, BigDecimal totalPrice) {
		return new Builder(order, pizzashack, qty, totalPrice);
	}

	public static class Builder {

		private OrderPizzashackModel built;

		public Builder(OrderModel order, PizzashackModel pizzashack, Integer qty) {
			built = new OrderPizzashackModel();
			built.setOrderModel(order);
			built.setPizzashackModel(pizzashack);
			built.qty = qty;
		}

		public Builder(OrderModel order, PizzashackModel pizzashack,
				Integer qty, BigDecimal totalPrice) {
			built = new OrderPizzashackModel();
			built.setOrderModel(order);
			built.setPizzashackModel(pizzashack);
			built.totalPrice = totalPrice;
			built.qty = qty;
		}

		public OrderPizzashackModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderPizzashackId", orderPizzashackId)
				.append("totalPrice", totalPrice).append("qty", qty).toString();

	}

}
