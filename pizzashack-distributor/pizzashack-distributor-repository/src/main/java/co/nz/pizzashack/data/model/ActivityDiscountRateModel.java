package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ACTIVITY_DISCOUNT_RATE")
public class ActivityDiscountRateModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACTIVITY_RATE_ID", insertable = false, updatable = false)
	private Long activityRateId;

	@Column(name = "CODE")
	private String activityCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "RATE")
	private BigDecimal rate;

	@Column(name = "EFFECTIVE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveTime;

	@Column(name = "EXPIRE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PIZZASHACK_ID", referencedColumnName = "PIZZASHACK_ID")
	private PizzashackModel pizzashack;

	public Long getActivityRateId() {
		return activityRateId;
	}

	public void setActivityRateId(Long activityRateId) {
		this.activityRateId = activityRateId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public PizzashackModel getPizzashack() {
		return pizzashack;
	}

	public void setPizzashack(PizzashackModel pizzashack) {
		this.pizzashack = pizzashack;
	}

	public static Builder getBuilder(String activityCode, String description,
			BigDecimal rate) {
		return new Builder(activityCode, description, rate);
	}

	public static Builder getBuilder(String description, BigDecimal rate) {
		return new Builder(description, rate);
	}

	public static class Builder {

		private ActivityDiscountRateModel built;

		public Builder(String activityCode, String description, BigDecimal rate) {
			built = new ActivityDiscountRateModel();
			built.activityCode = activityCode;
			built.description = description;
			built.rate = rate;
		}

		public Builder(String description, BigDecimal rate) {
			built = new ActivityDiscountRateModel();
			built.description = description;
			built.rate = rate;
		}

		public ActivityDiscountRateModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.activityCode,
				((ActivityDiscountRateModel) obj).activityCode).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.activityCode).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("activityRateId", activityRateId)
				.append("activityCode", activityCode)
				.append("description", description).append("rate", rate)
				.append("effectiveTime", effectiveTime)
				.append("expireTime", expireTime).toString();
	}
}
