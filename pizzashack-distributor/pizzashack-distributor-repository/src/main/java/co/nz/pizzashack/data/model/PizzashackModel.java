package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_PIZZASHACK")
public class PizzashackModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PIZZASHACK_ID", insertable = false, updatable = false)
	private Long pizzashackId;

	@Column(name = "NAME")
	private String pizzaName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PRICE")
	private BigDecimal price;

	@Column(name = "ICON")
	private String icon;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pizzashack")
	private Set<ActivityDiscountRateModel> activityDiscountRateSet;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderPizzashackPK.pizzashack")
	private List<OrderPizzashackModel> orderPizzashackModels;

	public void addActivityDiscountRate(
			ActivityDiscountRateModel activityDiscountRate) {
		if (activityDiscountRateSet == null) {
			activityDiscountRateSet = new HashSet<ActivityDiscountRateModel>();
		}
		activityDiscountRateSet.add(activityDiscountRate);
	}

	public List<OrderPizzashackModel> getOrderPizzashackModels() {
		return orderPizzashackModels;
	}

	public void setOrderPizzashackModels(
			List<OrderPizzashackModel> orderPizzashackModels) {
		this.orderPizzashackModels = orderPizzashackModels;
	}

	public Long getPizzashackId() {
		return pizzashackId;
	}

	public void setPizzashackId(Long pizzashackId) {
		this.pizzashackId = pizzashackId;
	}

	public String getPizzaName() {
		return pizzaName;
	}

	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Set<ActivityDiscountRateModel> getActivityDiscountRateSet() {
		return activityDiscountRateSet;
	}

	public void setActivityDiscountRateSet(
			Set<ActivityDiscountRateModel> activityDiscountRateSet) {
		this.activityDiscountRateSet = activityDiscountRateSet;
	}

	public static Builder getBuilder(String pizzaName, String description,
			BigDecimal price, String icon) {
		return new Builder(pizzaName, description, price, icon);
	}

	public static class Builder {
		private PizzashackModel built;

		public Builder(String pizzaName, String description, BigDecimal price,
				String icon) {
			built = new PizzashackModel();
			built.pizzaName = pizzaName;
			built.description = description;
			built.price = price;
			built.icon = icon;
		}

		public Builder(String pizzaName, String description, String icon) {
			built = new PizzashackModel();
			built.pizzaName = pizzaName;
			built.description = description;
			built.icon = icon;
		}

		public PizzashackModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder
				.append(this.pizzaName, ((PizzashackModel) obj).pizzaName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.pizzaName).toHashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
