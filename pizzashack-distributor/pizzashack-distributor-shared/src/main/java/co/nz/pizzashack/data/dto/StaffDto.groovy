package co.nz.pizzashack.data.dto

import groovy.transform.ToString

import org.hibernate.validator.constraints.NotEmpty
@ToString(includeNames = true, includeFields=true)
class StaffDto implements Serializable {
	Long staffId
	@Delegate
	IndividualDto individual = new IndividualDto()
	String createDate
	Set<DepartmentDto> departments

	@NotEmpty
	String role
	String level
	@Delegate
	UserDto user= new UserDto()

	void addDepartment(DepartmentDto department){
		if(!departments){
			departments = new HashSet<DepartmentDto>()
		}

		departments << department
	}
}
