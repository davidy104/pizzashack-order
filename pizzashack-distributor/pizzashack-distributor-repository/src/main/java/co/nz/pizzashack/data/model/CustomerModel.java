package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CUSTOMER")
public class CustomerModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CUST_ID", insertable = false, updatable = false)
	private Long custId;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CREDITS")
	private Integer credits;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<OrderModel> orders;

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void addOrder(OrderModel order) {
		if (orders == null) {
			orders = new ArrayList<OrderModel>();
		}
		orders.add(order);
	}

	public List<OrderModel> getOrders() {
		return orders;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public void setOrders(List<OrderModel> orders) {
		this.orders = orders;
	}

	public static Builder getBuilder(String customerName, String address,
			String email) {
		return new Builder(customerName, address, email);
	}

	public static Builder getBuilder(String customerName, Integer credits,
			String address, String email) {
		return new Builder(customerName, credits, address, email);
	}

	public static Builder getBuilder(String customerName) {
		return new Builder(customerName);
	}

	public static class Builder {

		private CustomerModel built;

		public Builder(String customerName, Integer credits, String address,
				String email) {
			built = new CustomerModel();
			built.customerName = customerName;
			built.credits = credits;
			built.address = address;
			built.email = email;
		}

		public Builder(String customerName, String address, String email) {
			built = new CustomerModel();
			built.customerName = customerName;
			built.address = address;
			built.email = email;
		}

		public Builder(String customerName) {
			built = new CustomerModel();
			built.customerName = customerName;
		}

		public CustomerModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.email, ((CustomerModel) obj).email)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.email).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("custId", custId).append("customerName", customerName)
				.append("email", email).append("credits", credits)
				.append("address", address).toString();
	}

}
