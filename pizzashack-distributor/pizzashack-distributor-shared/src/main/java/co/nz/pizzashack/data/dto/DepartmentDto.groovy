package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["deptName"])
class DepartmentDto implements Serializable{
	Long deptId
	String deptName
	String createDate
}
