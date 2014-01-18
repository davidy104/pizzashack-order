package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.DepartmentPredicates.findByDeptname;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.DuplicatedException;
import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.DepartmentConverter;
import co.nz.pizzashack.data.dto.DepartmentDto;
import co.nz.pizzashack.data.model.DepartmentModel;
import co.nz.pizzashack.data.repository.DepartmentRepository;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class DepartmentDSImpl implements DepartmentDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DepartmentDSImpl.class);

	@Resource
	private DepartmentConverter departmentConverter;

	@Resource
	private DepartmentRepository departmentRepository;

	@Resource
	private IdentityService identityService;

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public DepartmentDto createDepartment(DepartmentDto department)
			throws Exception {
		LOGGER.info("createDepartment start:{} ", department);
		DepartmentDto added = null;
		String departmentName = department.getDeptName();
		DepartmentModel departmentModel = departmentRepository
				.findOne(findByDeptname(departmentName));
		if (departmentModel != null) {
			throw new DuplicatedException("Department already existed");
		}
		departmentModel = departmentConverter.toModel(department);
		departmentModel.setCreateDate(new Date());
		departmentModel = departmentRepository.save(departmentModel);

		Group group = identityService.newGroup(String.valueOf(departmentModel
				.getDeptId()));
		group.setName(departmentModel.getDeptName());
		group.setType("");
		identityService.saveGroup(group);
		added = departmentConverter.toDto(departmentModel);
		LOGGER.info("createDepartment end:{} ", added);
		return added;
	}

	@Override
	public DepartmentDto getDepartmentDtoById(Long deptId) throws Exception {
		LOGGER.info("getDepartmentDtoById start:{}", deptId);
		DepartmentDto found = null;
		DepartmentModel departmentModel = departmentRepository.findOne(deptId);
		if (departmentModel == null) {
			throw new NotFoundException("Department not found by id[" + deptId
					+ "]");
		}
		found = departmentConverter.toDto(departmentModel);
		LOGGER.info("getDepartmentDtoById end:{}", found);
		return found;
	}

	@Override
	public DepartmentDto getDepartmentDtoByName(String deptName)
			throws Exception {
		LOGGER.info("getDepartmentDtoByName start:{}", deptName);
		DepartmentDto found = null;
		DepartmentModel departmentModel = departmentRepository
				.findOne(findByDeptname(deptName));
		if (departmentModel == null) {
			throw new NotFoundException("Department not found by deptName["
					+ deptName + "]");
		}
		found = departmentConverter.toDto(departmentModel);
		LOGGER.info("getDepartmentDtoByName end:{}", found);
		return found;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<DepartmentDto> getAllDepartments(DepartmentDto searchConditions)
			throws Exception {
		LOGGER.info("getAllDepartments start:{}");
		Set<DepartmentDto> departmentSet = null;
		List<DepartmentModel> modelList = null;

		if (searchConditions != null) {
			String deptName = searchConditions.getDeptName();
			Iterable<DepartmentModel> iterable = departmentRepository
					.findAll(findByDeptname(deptName));
			if (iterable != null) {
				modelList = IteratorUtils.toList(iterable.iterator());
			}
		} else {
			modelList = departmentRepository.findAll();
		}

		if (modelList != null && modelList.size() > 0) {
			departmentSet = new HashSet<DepartmentDto>();
			for (DepartmentModel model : modelList) {
				departmentSet.add(departmentConverter.toDto(model));
			}
		}
		LOGGER.info("getAllDepartments end:{}");
		return departmentSet;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void deleteDepartment(Long deptId) throws Exception {
		LOGGER.info("deleteDepartment start:{} ", deptId);
		DepartmentModel departmentModel = departmentRepository.findOne(deptId);
		if (departmentModel == null) {
			throw new NotFoundException("Department not found by id[" + deptId
					+ "]");
		}

		if (identityService.createGroupQuery().groupId(String.valueOf(deptId))
				.count() > 0) {
			if (identityService.createUserQuery()
					.memberOfGroup(String.valueOf(deptId)).count() > 0) {
				throw new Exception(
						"DeleteDepartment can not be deleted as it still has members");
			}

			identityService.deleteGroup(String.valueOf(deptId));
		}
		departmentRepository.delete(departmentModel);
		LOGGER.info("deleteDepartment end:{} ");
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void updateDepartmentName(Long deptId, String updatedName)
			throws Exception {
		LOGGER.info("updateDepartmentName start:{} ", deptId);
		DepartmentModel departmentModel = departmentRepository.findOne(deptId);
		if (departmentModel == null) {
			throw new NotFoundException("Department not found by id[" + deptId
					+ "]");
		}
		Group group = identityService.createGroupQuery()
				.groupId(String.valueOf(deptId)).singleResult();
		if (group != null) {
			group.setName(updatedName);
			identityService.saveGroup(group);
		}
		departmentModel.setDeptName(updatedName);
		LOGGER.info("updateDepartmentName end:{} ", departmentModel);
	}

}
