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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORDER_PROCESS")
public class OrderProcessModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_PROCESS_ID")
	private Long orderProcessId;

	@Column(name = "EXECUTION_ID")
	private String executionId;

	@Column(name = "MAIN_PROCESS_INSTANCE_ID")
	private String mainProcessInstanceId;

	@Column(name = "MAIN_PROCESS_DEFINITION_ID")
	private String mainProcessDefinitionId;

	@Column(name = "ACTIVE_PROCESS_INSTANCE_ID")
	private String activeProcesssInstanceId;

	@Column(name = "ACTIVE_PROCESS_DEFINITION_ID")
	private String activeProcessDefinitionId;

	@Temporal(value = TemporalType.TIME)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Temporal(value = TemporalType.TIME)
	@Column(name = "COMPLETE_TIME")
	private Date completeTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
	private OrderModel order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_ID", referencedColumnName = "USER_ID")
	private UserModel operator;

	public Long getOrderProcessId() {
		return orderProcessId;
	}

	public void setOrderProcessId(Long orderProcessId) {
		this.orderProcessId = orderProcessId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getMainProcessInstanceId() {
		return mainProcessInstanceId;
	}

	public void setMainProcessInstanceId(String mainProcessInstanceId) {
		this.mainProcessInstanceId = mainProcessInstanceId;
	}

	public String getMainProcessDefinitionId() {
		return mainProcessDefinitionId;
	}

	public void setMainProcessDefinitionId(String mainProcessDefinitionId) {
		this.mainProcessDefinitionId = mainProcessDefinitionId;
	}

	public String getActiveProcesssInstanceId() {
		return activeProcesssInstanceId;
	}

	public void setActiveProcesssInstanceId(String activeProcesssInstanceId) {
		this.activeProcesssInstanceId = activeProcesssInstanceId;
	}

	public String getActiveProcessDefinitionId() {
		return activeProcessDefinitionId;
	}

	public void setActiveProcessDefinitionId(String activeProcessDefinitionId) {
		this.activeProcessDefinitionId = activeProcessDefinitionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	public UserModel getOperator() {
		return operator;
	}

	public void setOperator(UserModel operator) {
		this.operator = operator;
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.order, ((OrderProcessModel) obj).order)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.order).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderProcessId", orderProcessId)
				.append("executionId", executionId)
				.append("mainProcessInstanceId", mainProcessInstanceId)
				.append("mainProcessDefinitionId", mainProcessDefinitionId)
				.append("activeProcesssInstanceId", activeProcesssInstanceId)
				.append("activeProcessDefinitionId", activeProcessDefinitionId)
				.append("createTime", createTime)
				.append("completeTime", completeTime).toString();
	}

}
