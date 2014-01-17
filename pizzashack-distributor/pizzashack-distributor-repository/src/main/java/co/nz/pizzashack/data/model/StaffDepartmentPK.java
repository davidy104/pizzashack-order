package co.nz.pizzashack.data.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@SuppressWarnings("serial")
@Embeddable
public class StaffDepartmentPK implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	private StaffModel staff;

	@ManyToOne(fetch = FetchType.LAZY)
	private DepartmentModel dept;

	public DepartmentModel getDept() {
		return dept;
	}

	public void setDept(DepartmentModel dept) {
		this.dept = dept;
	}

	public StaffModel getStaff() {
		return staff;
	}

	public void setStaff(StaffModel staff) {
		this.staff = staff;
	}
	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.dept, ((StaffDepartmentPK) obj).dept)
				.append(this.staff, ((StaffDepartmentPK) obj).staff).isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.dept).append(this.staff).toHashCode();
	}
}
