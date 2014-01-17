package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "T_ROLE")
public class RoleModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATE_TIME")
	@Temporal(value = TemporalType.TIME)
	private Date createTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userRolePK.role")
	private List<UserRoleModel> userRoleModels;

	public List<UserRoleModel> getUserRoleModels() {
		return userRoleModels;
	}

	public void setUserRoleModels(List<UserRoleModel> userRoleModels) {
		this.userRoleModels = userRoleModels;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static Builder getBuilder(String roleName, String description) {
		return new Builder(roleName, description);
	}

	public static class Builder {
		private RoleModel built;

		public Builder(String roleName, String description) {
			built = new RoleModel();
			built.roleName = roleName;
			built.description = description;
		}

		public RoleModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("roleId", roleId).append("roleName", roleName)
				.append("description", description)
				.append("createTime", createTime).toString();
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.roleName, ((RoleModel) obj).roleName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.roleName).toHashCode();
	}
}
