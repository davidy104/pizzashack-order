package co.nz.pizzashack.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "T_STAFF")
public class StaffModel implements Serializable {
	public enum StaffRole {
		operator(0), manager(1);
		StaffRole(int value) {
			this.value = value;
		}
		private final int value;
		public int value() {
			return value;
		}
	}

	public enum StaffLevel {
		junior(0), intermedior(1), senior(2);
		StaffLevel(int value) {
			this.value = value;
		}
		private final int value;
		public int value() {
			return value;
		}
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STAFF_ID")
	private Long staffId;

	@Embedded
	private IndividualModel individual;

	@Column(name = "ROLE")
	private Integer role;

	@Column(name = "LEVEL")
	private Integer level;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private UserModel user;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "staffDepartmentPK.staff")
	private List<StaffDepartmentModel> staffDepartments;

	@Column(name = "CREATE_DATE")
	@Temporal(TemporalType.DATE)
	private Date createDate;

	public void addStaffDepartment(StaffDepartmentModel staffDepartmentModel) {
		if (staffDepartments == null) {
			staffDepartments = new ArrayList<StaffDepartmentModel>();
		}
		staffDepartments.add(staffDepartmentModel);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public IndividualModel getIndividual() {
		return individual;
	}

	public void setIndividual(IndividualModel individual) {
		this.individual = individual;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<StaffDepartmentModel> getStaffDepartments() {
		return staffDepartments;
	}

	public void setStaffDepartments(List<StaffDepartmentModel> staffDepartments) {
		this.staffDepartments = staffDepartments;
	}

	public static Builder getBuilder(IndividualModel individual, Integer role,
			Integer level, UserModel user) {
		return new Builder(individual, role, level, user);
	}

	public static Builder getBuilder(IndividualModel individual, Integer role,
			Integer level) {
		return new Builder(individual, role, level);
	}

	public static Builder getBuilder(IndividualModel individual) {
		return new Builder(individual);
	}

	public static class Builder {

		private StaffModel built;

		public Builder(IndividualModel individual, Integer role, Integer level,
				UserModel user) {
			built = new StaffModel();
			built.individual = individual;
			built.role = role;
			built.level = level;
			built.user = user;
		}

		public Builder(IndividualModel individual, Integer role, Integer level) {
			built = new StaffModel();
			built.individual = individual;
			built.role = role;
			built.level = level;
		}

		public Builder(IndividualModel individual) {
			built = new StaffModel();
			built.individual = individual;
		}

		public StaffModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.individual, ((StaffModel) obj).individual)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.individual).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("staffId", staffId).append("individual", individual)
				.append("role", role).append("level", level)
				.append("createDate", createDate).toString();
	}

}
