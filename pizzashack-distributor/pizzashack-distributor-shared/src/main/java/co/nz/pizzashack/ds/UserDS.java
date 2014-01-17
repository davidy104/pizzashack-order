package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.UserDto;

public interface UserDS {

	UserDto createUser(String username, String password,
			Set<Long> selectedRoleIds) throws Exception;

	UserDto getUserById(Long userId, boolean loadRoles) throws Exception;

	UserDto getUserByName(String username, boolean loadRoles) throws Exception;

	Set<UserDto> getAllUsers() throws Exception;

	void updateUser(Long userId, UserDto userDto, Set<Long> updatedRoleIds)
			throws Exception;

	void deleteUser(Long userId) throws Exception;

}
