package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.DepartmentDto;

public interface DepartmentDS {

	DepartmentDto createDepartment(DepartmentDto department) throws Exception;

	DepartmentDto getDepartmentDtoById(Long deptId) throws Exception;

	DepartmentDto getDepartmentDtoByName(String deptName) throws Exception;

	Set<DepartmentDto> getAllDepartments(DepartmentDto searchConditions)
			throws Exception;

	void deleteDepartment(Long deptId) throws Exception;

	void updateDepartmentName(Long deptId, String updatedName) throws Exception;

}
