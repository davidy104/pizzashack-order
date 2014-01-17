package co.nz.pizzashack.data.dto

import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
class StaffDto implements Serializable {
	Long staffId
	@Delegate
	IndividualDto individual = new IndividualDto()
	String createDate
	Set<DepartmentDto> departments
	String role
	String level
	UserDto user

	void addDepartment(DepartmentDto department){
		if(!departments){
			departments = new HashSet<DepartmentDto>()
		}

		departments << department
	}
}
