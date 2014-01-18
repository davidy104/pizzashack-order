package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.StaffLoadStrategies;
import co.nz.pizzashack.data.dto.StaffDto;

public interface StaffDS {

	StaffDto createStaff(StaffDto staff, Set<Long> selectedDeptIds)
			throws Exception;

	StaffDto getStaffById(Long staffId,
			StaffLoadStrategies... staffLoadStrategies) throws Exception;

	Set<StaffDto> getStaffsByName(String firstName, String lastName,
			StaffLoadStrategies... staffLoadStrategies) throws Exception;

	StaffDto getStaffByIdentity(String identity,
			StaffLoadStrategies... staffLoadStrategies) throws Exception;

	Set<StaffDto> getStaffsByDepartmentId(Long deptId,
			StaffLoadStrategies... staffLoadStrategies) throws Exception;

	void deleteStaff(Long staffId) throws Exception;

	Set<StaffDto> getAllStaffs(StaffDto searchConditions) throws Exception;

	void updateStaff(Long staffId, Set<Long> updatedDeptIds) throws Exception;

}
