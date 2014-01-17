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
@SuppressWarnings("serial")
@Entity
@Table(name = "T_DEPT")
public class DepartmentModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPT_ID")
	private Long deptId;

	@Column(name = "DEPT_NAME")
	private String deptName;

	@Column(name = "CREATE_DATE")
	@Temporal(value = TemporalType.DATE)
	private Date createDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staffDepartmentPK.dept")
	private List<StaffDepartmentModel> staffDepartments;

	public List<StaffDepartmentModel> getStaffDepartments() {
		return staffDepartments;
	}

	public void setStaffDepartments(List<StaffDepartmentModel> staffDepartments) {
		this.staffDepartments = staffDepartments;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public static Builder getBuilder(String deptName) {
		return new Builder(deptName);
	}

	public static class Builder {

		private DepartmentModel built;

		public Builder(String deptName) {
			built = new DepartmentModel();
			built.deptName = deptName;
		}

		public DepartmentModel build() {
			return built;
		}
	}

	@Override
	public boolean equals(Object obj) {
		EqualsBuilder builder = new EqualsBuilder();
		return builder.append(this.deptName, ((DepartmentModel) obj).deptName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		return builder.append(this.deptName).toHashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
