package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["roleName"])
class RoleDto {
	Long roleId
	String roleName
	String createTime
	String description
}
