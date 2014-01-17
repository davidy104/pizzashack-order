package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["username"])
class UserDto implements Serializable{
	Long userId
	String username
	String password
	String createTime
	Set<RoleDto> roles

	void addRole(RoleDto role){
		if(!roles){
			roles = new HashSet<RoleDto>()
		}
		roles << role
	}

	static Builder getBuilder(String username, String password) {
		return new Builder(username, password)
	}

	static class Builder {

		UserDto built

		Builder(String username, String password, String createTime) {
			built = new UserDto()
			built.username = username
			built.password = password
			built.createTime = createTime
		}

		Builder(String username, String password) {
			built = new UserDto()
			built.username = username
			built.password = password
		}

		UserDto build() {
			return built
		}
	}
}
