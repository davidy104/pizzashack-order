package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_WORKFLOW")
public class WorkflowModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "workflow_id", insertable = false, updatable = false)
	private Long wfId;

	@Column(name = "workflow_name")
	private String name;

	@Column(name = "DEPLOY_ID")
	private String deployId;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "processDefinitionKey")
	private String processDefinitionKey;

	@Column(name = "processDefinitionId")
	private String processDefinitionId;

	@Column(name = "CREATE_TIME")
	@Temporal(value = TemporalType.TIME)
	private Date createTime;

	@Column(name = "IMG_PATH")
	private String imgPath;

	public Long getWfId() {
		return wfId;
	}

	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public static Builder getBuilder(String name, String category,
			String deployId, String processDefinitionId) {
		return new Builder(name, category, deployId, processDefinitionId);
	}

	public static Builder getBuilder(String name, String category) {
		return new Builder(name, category);
	}

	public static Builder getBuilder(String name, String category,
			String imgPath) {
		return new Builder(name, category, imgPath);
	}

	public static class Builder {
		private WorkflowModel built;

		public Builder(String name, String category, String deployId,
				String processDefinitionId) {
			built = new WorkflowModel();
			built.name = name;
			built.deployId = deployId;
			built.category = category;
			built.processDefinitionId = processDefinitionId;
		}

		public Builder(String name, String category) {
			built = new WorkflowModel();
			built.name = name;
			built.category = category;
		}

		public Builder(String name, String category, String imgPath) {
			built = new WorkflowModel();
			built.name = name;
			built.category = category;
			built.imgPath = imgPath;
		}

		public WorkflowModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("wfId", wfId).append("name", name)
				.append("deployId", deployId)
				.append("processDefinitionKey", processDefinitionKey)
				.append("processDefinitionId", processDefinitionId)
				.append("createTime", createTime).append("imgPath", imgPath)
				.toString();
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.processDefinitionId,
				((WorkflowModel) obj).processDefinitionId).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.processDefinitionId).toHashCode();
	}
}
