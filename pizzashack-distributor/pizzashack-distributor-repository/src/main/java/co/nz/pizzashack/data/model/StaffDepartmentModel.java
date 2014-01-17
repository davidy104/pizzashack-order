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
@Table(name = "T_DEPT_STAFF")
@AssociationOverrides({
		@AssociationOverride(name = "staffDepartmentPK.staff", joinColumns = @JoinColumn(name = "STAFF_ID")),
		@AssociationOverride(name = "staffDepartmentPK.dept", joinColumns = @JoinColumn(name = "DEPT_ID"))})
public class StaffDepartmentModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STAFF_DEPT_ID", insertable = false, updatable = false)
	private Long staffDeptId;

	@Embedded
	private StaffDepartmentPK staffDepartmentPK = new StaffDepartmentPK();

	@Column(name = "JOIN_DATE")
	@Temporal(value = TemporalType.DATE)
	private Date joinDate;

	public Long getStaffDeptId() {
		return staffDeptId;
	}

	public void setStaffDeptId(Long staffDeptId) {
		this.staffDeptId = staffDeptId;
	}

	public StaffDepartmentPK getStaffDepartmentPK() {
		return staffDepartmentPK;
	}

	public void setStaffDepartmentPK(StaffDepartmentPK staffDepartmentPK) {
		this.staffDepartmentPK = staffDepartmentPK;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Transient
	public DepartmentModel getDepartmentModel() {
		return getStaffDepartmentPK().getDept();
	}

	public void setDepartmentModel(DepartmentModel dept) {
		getStaffDepartmentPK().setDept(dept);
	}

	@Transient
	public StaffModel getStaffModel() {
		return getStaffDepartmentPK().getStaff();
	}

	public void setStaffModel(StaffModel staff) {
		getStaffDepartmentPK().setStaff(staff);
	}

	public static Builder getBuilder(DepartmentModel dept, StaffModel staff,
			Date joinDate) {
		return new Builder(dept, staff, joinDate);
	}

	public static class Builder {

		private StaffDepartmentModel built;

		public Builder(DepartmentModel dept, StaffModel staff, Date joinDate) {
			built = new StaffDepartmentModel();
			built.setDepartmentModel(dept);
			built.setStaffModel(staff);
			built.joinDate = joinDate;
		}

		public StaffDepartmentModel build() {
			return built;
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("staffDeptId", staffDeptId)
				.append("joinDate", joinDate).toString();

	}
}
