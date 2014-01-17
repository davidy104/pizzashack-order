package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.RoleDto;

public interface RoleDS {

	RoleDto createRole(RoleDto role) throws Exception;

	RoleDto getRoleById(Long roleId) throws Exception;

	RoleDto getRoleByName(String roleName) throws Exception;

	void deleteRole(Long roleId) throws Exception;

	Set<RoleDto> getAllRoles() throws Exception;
}
