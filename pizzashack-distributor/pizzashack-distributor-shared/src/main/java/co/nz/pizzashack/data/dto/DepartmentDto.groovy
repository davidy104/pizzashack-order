package co.nz.pizzashack.data.dto

import org.hibernate.validator.constraints.NotEmpty;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["deptName"])
class DepartmentDto implements Serializable{
	Long deptId
	@NotEmpty
	String deptName
	String createDate
}
