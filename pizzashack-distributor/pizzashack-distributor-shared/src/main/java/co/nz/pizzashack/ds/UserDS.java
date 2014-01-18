package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.AuthenticationException;
import co.nz.pizzashack.data.dto.UserDto;

public interface UserDS {

	UserDto createUser(String username, String password) throws Exception;

	UserDto loginUser(String username, String password)
			throws AuthenticationException;

	UserDto getUserById(Long userId) throws Exception;

	UserDto getUserByName(String username) throws Exception;

	Set<UserDto> getAllUsers(UserDto searchConditions) throws Exception;

	void updateUser(Long userId, UserDto userDto) throws Exception;

	void deleteUser(Long userId) throws Exception;

}
