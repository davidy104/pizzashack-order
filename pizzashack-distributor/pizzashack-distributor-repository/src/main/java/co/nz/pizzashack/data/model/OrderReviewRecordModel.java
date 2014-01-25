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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORDER_REVIEW_RECORD")
public class OrderReviewRecordModel implements Serializable {

	public enum ReviewStatus {
		accept(1), reject(3), pending(5);
		ReviewStatus(int value) {
			this.value = value;
		}

		private final int value;

		public int value() {
			return value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "REVIEW_RECORD_ID")
	private Long reviewRecordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
	private OrderModel order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVIEWER_ID", referencedColumnName = "STAFF_ID")
	private StaffModel reviewer;

	@Column(name = "CONTENT")
	private String content;

	@Temporal(value = TemporalType.TIME)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "REVIEW_STATUS")
	private Integer reviewStatus = ReviewStatus.pending.value();

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public StaffModel getReviewer() {
		return reviewer;
	}

	public void setReviewer(StaffModel reviewer) {
		this.reviewer = reviewer;
	}

	public Long getReviewRecordId() {
		return reviewRecordId;
	}

	public void setReviewRecordId(Long reviewRecordId) {
		this.reviewRecordId = reviewRecordId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public static Builder getBuilder(OrderModel order, String content,
			Date createTime, StaffModel reviewer) {
		return new Builder(order, content, createTime, reviewer);
	}

	public static Builder getBuilder(OrderModel order, String content,
			StaffModel reviewer) {
		return new Builder(order, content, reviewer);
	}

	public static Builder getBuilder(String content, Date createTime) {
		return new Builder(content, createTime);
	}

	public static class Builder {

		private OrderReviewRecordModel built;

		public Builder(OrderModel order, String content, Date createTime,
				StaffModel reviewer) {
			built = new OrderReviewRecordModel();
			built.order = order;
			built.content = content;
			built.createTime = createTime;
			built.reviewer = reviewer;
		}

		public Builder(OrderModel order, String content, StaffModel reviewer) {
			built = new OrderReviewRecordModel();
			built.order = order;
			built.content = content;
			built.reviewer = reviewer;
		}

		public Builder(String content, Date createTime) {
			built = new OrderReviewRecordModel();
			built.content = content;
			built.createTime = createTime;

		}

		public OrderReviewRecordModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("reviewRecordId", reviewRecordId)
				.append("content", content).append("createTime", createTime)
				.toString();
	}
}
