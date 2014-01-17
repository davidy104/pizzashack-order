package co.nz.pizzashack.data.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@SuppressWarnings("serial")
@Embeddable
public class UserRolePK implements Serializable {
	@ManyToOne(fetch = FetchType.LAZY)
	private UserModel user;

	@ManyToOne(fetch = FetchType.LAZY)
	private RoleModel role;

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.user, ((UserRolePK) obj).user)
				.append(this.role, ((UserRolePK) obj).role).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.role).append(this.user).toHashCode();
	}
}
