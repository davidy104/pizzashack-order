package co.nz.pizzashack.data.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@SuppressWarnings("serial")
@Embeddable
public class OrderPizzashackPK implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	private OrderModel order;

	@ManyToOne(fetch = FetchType.LAZY)
	private PizzashackModel pizzashack;

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public PizzashackModel getPizzashack() {
		return pizzashack;
	}

	public void setPizzashack(PizzashackModel pizzashack) {
		this.pizzashack = pizzashack;
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.order, ((OrderPizzashackPK) obj).order)
				.append(this.pizzashack, ((OrderPizzashackPK) obj).pizzashack)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.order).append(this.pizzashack).toHashCode();
	}
}
