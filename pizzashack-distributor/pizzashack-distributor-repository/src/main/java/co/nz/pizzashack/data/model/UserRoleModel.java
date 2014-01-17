package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_USER_ROLE")
@AssociationOverrides({
		@AssociationOverride(name = "userRolePK.user", joinColumns = @JoinColumn(name = "USER_ID")),
		@AssociationOverride(name = "userRolePK.role", joinColumns = @JoinColumn(name = "ROLE_ID"))})
public class UserRoleModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ROLE_ID", insertable = false, updatable = false)
	private Long userRoleId;

	@Embedded
	private UserRolePK userRolePK;

	@Column(name = "ASSIGN_TIME")
	@Temporal(value = TemporalType.TIME)
	private Date assignTime;

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserRolePK getUserRolePK() {
		return userRolePK;
	}

	public void setUserRolePK(UserRolePK userRolePK) {
		this.userRolePK = userRolePK;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	@Transient
	public UserModel getUserModel() {
		return getUserRolePK().getUser();
	}

	public void setUserModel(UserModel user) {
		getUserRolePK().setUser(user);
	}

	@Transient
	public RoleModel getRoleModel() {
		return getUserRolePK().getRole();
	}

	public void setRoleModel(RoleModel role) {
		getUserRolePK().setRole(role);
	}

	public static Builder getBuilder(UserModel user, RoleModel role,
			Date assignTime) {
		return new Builder(user, role, assignTime);
	}

	public static class Builder {

		private UserRoleModel built;

		public Builder(UserModel user, RoleModel role, Date assignTime) {
			built = new UserRoleModel();
			built.setUserModel(user);
			built.setRoleModel(role);
			built.assignTime = assignTime;
		}

		public UserRoleModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("userRoleId", userRoleId)
				.append("assignTime", assignTime).toString();

	}
}
