package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.StaffPredicates.findByComplicatedConditions;
import static co.nz.pizzashack.data.predicates.StaffPredicates.findByStaffIdentity;
import static co.nz.pizzashack.data.predicates.StaffPredicates.findByStaffName;
import static co.nz.pizzashack.data.predicates.UserPredicates.findByUsername;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.apache.commons.collections.IteratorUtils;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.DuplicatedException;
import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.StaffLoadStrategies;
import co.nz.pizzashack.data.converter.DepartmentConverter;
import co.nz.pizzashack.data.converter.StaffConverter;
import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.IndividualDto;
import co.nz.pizzashack.data.dto.StaffDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.DepartmentModel;
import co.nz.pizzashack.data.model.StaffDepartmentModel;
import co.nz.pizzashack.data.model.StaffModel;
import co.nz.pizzashack.data.model.StaffModel.StaffLevel;
import co.nz.pizzashack.data.model.StaffModel.StaffRole;
import co.nz.pizzashack.data.model.UserModel;
import co.nz.pizzashack.data.repository.DepartmentRepository;
import co.nz.pizzashack.data.repository.StaffDepartmentRepository;
import co.nz.pizzashack.data.repository.StaffRepository;
import co.nz.pizzashack.data.repository.UserRepository;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class StaffDSImpl implements StaffDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StaffDSImpl.class);

	@Resource
	private StaffRepository staffRepository;

	@Resource
	private DepartmentRepository departmentRepository;

	@Resource
	private StaffDepartmentRepository staffDepartmentRepository;

	@Resource
	private DepartmentConverter departmentConverter;

	@Resource
	private StaffConverter staffConverter;

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserConverter userConverter;

	@Resource
	private IdentityService identityService;

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public StaffDto createStaff(StaffDto staff, Set<Long> selectedDeptIds)
			throws Exception {
		LOGGER.info("createStaff start:{} ", staff);
		StaffDto added = null;
		UserModel userModel = null;
		User wfUser = null;
		String identity = staff.getIndividual().getIdentity();
		StaffModel staffModel = staffRepository
				.findOne(findByStaffIdentity(identity));

		if (staffModel != null) {
			throw new DuplicatedException("Staff already existed");
		}

		staffModel = staffConverter.toModel(staff);
		staffModel.setCreateDate(new Date());

		UserDto user = staff.getUser();
		if (user == null) {
			throw new Exception("User can not be null for staff");
		}

		Long userId = user.getUserId();
		if (userId != null) {
			userModel = userRepository.findOne(userId);
			if (userModel == null) {
				throw new NotFoundException("User not found by id [" + userId
						+ "]");
			}
			wfUser = identityService.createUserQuery()
					.userId(String.valueOf(userModel.getUserId()))
					.singleResult();
		} else {
			String username = user.getUsername();
			userModel = userRepository.findOne(findByUsername(username));
			if (userModel != null) {
				throw new DuplicatedException("User already existed");
			}
			userModel = userConverter.toModel(user);
			userModel = userRepository.save(userModel);
			wfUser = identityService.newUser(String.valueOf(userModel
					.getUserId()));
		}
		staffModel.setUser(userModel);
		wfUser.setEmail(staffModel.getIndividual().getEmail());
		wfUser.setFirstName(staffModel.getIndividual().getFirstName());
		wfUser.setLastName(staffModel.getIndividual().getLastName());
		identityService.saveUser(wfUser);

		if (selectedDeptIds != null) {
			this.assignMembershipOfDepartment(staffModel, selectedDeptIds);
		}

		staffModel = staffRepository.save(staffModel);
		added = staffConverter.toDto(staffModel);
		LOGGER.info("createStaff end:{} ", added);
		return added;
	}

	@Override
	public StaffDto getStaffById(Long staffId,
			StaffLoadStrategies... staffLoadStrategies) throws Exception {
		LOGGER.info("getStaffById start:{}", staffId);
		StaffDto found = null;
		StaffModel foundModel = staffRepository.findOne(staffId);
		if (foundModel == null) {
			throw new NotFoundException("Staff not found by id[" + staffId
					+ "]");
		}
		found = staffConverter.toDto(foundModel, staffLoadStrategies);
		LOGGER.info("getStaffById end:{}", found);
		return found;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<StaffDto> getStaffsByName(String firstName, String lastName,
			StaffLoadStrategies... staffLoadStrategies) throws Exception {
		LOGGER.info("getStaffsByName start:{}", firstName);
		Set<StaffDto> foundSet = null;
		Iterable<StaffModel> foundModels = staffRepository
				.findAll(findByStaffName(firstName, lastName));

		if (foundModels != null) {
			List<StaffModel> staffModelList = IteratorUtils.toList(foundModels
					.iterator());
			foundSet = new HashSet<StaffDto>();
			for (StaffModel model : staffModelList) {
				foundSet.add(staffConverter.toDto(model, staffLoadStrategies));
			}
		}
		LOGGER.info("getStaffsByName end:{}");
		return foundSet;
	}

	@Override
	public StaffDto getStaffByIdentity(String identity,
			StaffLoadStrategies... staffLoadStrategies) throws Exception {
		LOGGER.info("getStaffByIdentity start:{}", identity);
		StaffDto found = null;
		StaffModel foundModel = staffRepository
				.findOne(findByStaffIdentity(identity));
		if (foundModel == null) {
			throw new NotFoundException("Staff not found by identity["
					+ identity + "]");
		}
		found = staffConverter.toDto(foundModel, staffLoadStrategies);
		LOGGER.info("getStaffByIdentity end:{}", found);
		return found;
	}

	@Override
	public Set<StaffDto> getStaffsByDepartmentId(Long deptId,
			StaffLoadStrategies... staffLoadStrategies) throws Exception {
		LOGGER.info("getStaffsByDepartmentId start:{}", deptId);
		DepartmentModel deptModel = departmentRepository.findOne(deptId);
		Set<StaffDto> staffSet = null;
		if (deptModel == null) {
			throw new NotFoundException("Department not found by id[" + deptId
					+ "]");
		}
		List<StaffDepartmentModel> staffDepartmentList = deptModel
				.getStaffDepartments();
		if (staffDepartmentList != null && staffDepartmentList.size() > 0) {
			staffSet = new HashSet<StaffDto>();
			for (StaffDepartmentModel staffDepartmentModel : staffDepartmentList) {
				StaffModel staffModel = staffDepartmentModel.getStaffModel();
				staffSet.add(staffConverter.toDto(staffModel,
						staffLoadStrategies));
			}
		}
		LOGGER.info("getStaffsByDepartmentId end:{}");
		return staffSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<StaffDto> getAllStaffs(StaffDto searchConditions)
			throws Exception {
		LOGGER.info("getAllStaffs start:{} ", searchConditions);
		Set<StaffDto> resultSet = null;
		List<StaffModel> staffModelList = null;
		if (searchConditions == null) {
			staffModelList = staffRepository.findAll();
		} else {
			IndividualDto individual = searchConditions.getIndividual();
			Integer level = null;
			Integer role = null;

			String levelStr = searchConditions.getLevel();
			String roleStr = searchConditions.getRole();
			if (!StringUtils.isEmpty(levelStr)) {
				if (levelStr.equals("junior")) {
					level = StaffLevel.junior.value();
				} else if (levelStr.equals("intermedior")) {
					level = StaffLevel.intermedior.value();
				} else if (levelStr.equals("senior")) {
					level = StaffLevel.senior.value();
				}
			}

			if (!StringUtils.isEmpty(roleStr)) {
				if (roleStr.equals("operator")) {
					role = StaffRole.operator.value();
				} else if (roleStr.equals("manager")) {
					role = StaffRole.manager.value();
				}
			}

			Iterable<StaffModel> iterable = staffRepository
					.findAll(findByComplicatedConditions(individual, role,
							level));
			if (iterable != null) {
				staffModelList = IteratorUtils.toList(iterable.iterator());
			}

		}

		if (staffModelList != null && staffModelList.size() > 0) {
			resultSet = new HashSet<StaffDto>();
			for (StaffModel staffModel : staffModelList) {
				resultSet.add(staffConverter.toDto(staffModel));
			}
		}
		LOGGER.info("getAllStaffs end:{} ");
		return resultSet;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void deleteStaff(Long staffId) throws Exception {
		LOGGER.info("deleteStaff start:{} ", staffId);
		StaffModel foundModel = staffRepository.findOne(staffId);
		if (foundModel == null) {
			throw new NotFoundException("Staff not found by staffId[" + staffId
					+ "]");
		}

		List<StaffDepartmentModel> staffDepartmentModels = foundModel
				.getStaffDepartments();
		UserModel user = foundModel.getUser();

		if (staffDepartmentModels != null) {
			LOGGER.info("start delete membership between staff and department");
			for (StaffDepartmentModel staffDepartmentModel : staffDepartmentModels) {
				DepartmentModel department = staffDepartmentModel
						.getDepartmentModel();
				if (user != null) {
					List<User> userList = identityService
							.createUserQuery()
							.memberOfGroup(
									String.valueOf(department.getDeptId()))
							.list();

					for (User memberUser : userList) {
						if (memberUser.getId().equals(
								String.valueOf(user.getUserId()))) {
							LOGGER.info("delete membership from ["
									+ department.getDeptName() + "] for user["
									+ user.getUsername() + "]");
							identityService.deleteMembership(
									String.valueOf(user.getUserId()),
									String.valueOf(department.getDeptId()));
						}
					}
				}
				staffDepartmentRepository.delete(staffDepartmentModel);
			}
		}

		if (user != null) {
			identityService.deleteUser(String.valueOf(user.getUserId()));
			userRepository.delete(user);
		}
		staffRepository.delete(foundModel);
		LOGGER.info("deleteStaff end:{} ");
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void updateStaff(Long staffId, StaffDto staff,
			Set<Long> updatedDeptIds) throws Exception {
		LOGGER.info("updateStaff start:{}", staffId);
		StaffModel foundModel = staffRepository.findOne(staffId);
		if (foundModel == null) {
			throw new NotFoundException("Staff not found by staffId[" + staffId
					+ "]");
		}
		foundModel = staffConverter.toModel(staff, foundModel);
		this.assignMembershipOfDepartment(foundModel, updatedDeptIds);
		LOGGER.info("updateStaff end:{}");
	}

	private void assignMembershipOfDepartment(StaffModel staffModel,
			Set<Long> selectedDepartmentIds) {
		LOGGER.info("assignMembershipOfDepartment start:{} ", staffModel);
		Set<Long> needRemoveDeptIds = null;
		Set<Long> newDeptIds = null;
		Set<Long> existedDeptIds = null;
		DepartmentModel departmentModel = null;
		UserModel userModel = staffModel.getUser();
		if (selectedDepartmentIds != null && userModel != null) {
			List<StaffDepartmentModel> existedStaffDepartmentList = staffModel
					.getStaffDepartments();
			if (existedStaffDepartmentList != null
					&& existedStaffDepartmentList.size() > 0) {
				LOGGER.info("existedStaffDepartmentList size:{} ",
						existedStaffDepartmentList.size());
				existedDeptIds = new HashSet<Long>();
				for (StaffDepartmentModel staffDepartmentModel : existedStaffDepartmentList) {
					existedDeptIds.add(staffDepartmentModel
							.getDepartmentModel().getDeptId());
				}
				LOGGER.info("existedDeptIds size:{} ", existedDeptIds.size());
				Set<Long> unionIds = new HashSet<Long>();
				unionIds.addAll(selectedDepartmentIds);
				unionIds.addAll(existedDeptIds);
				newDeptIds = unionIds;
				newDeptIds.removeAll(existedDeptIds);

				needRemoveDeptIds = new HashSet<Long>();
				needRemoveDeptIds = existedDeptIds;
				needRemoveDeptIds.removeAll(selectedDepartmentIds);

				if (needRemoveDeptIds != null && needRemoveDeptIds.size() > 0) {
					LOGGER.info("revoke old membership:{} ",
							needRemoveDeptIds.size());
					for (Long removeDeptId : needRemoveDeptIds) {
						departmentModel = departmentRepository
								.findOne(removeDeptId);
						if (departmentModel != null) {
							if (identityService
									.createGroupQuery()
									.groupId(
											String.valueOf(departmentModel
													.getDeptId())).count() > 0) {
								List<User> userList = identityService
										.createUserQuery()
										.memberOfGroup(
												String.valueOf(departmentModel
														.getDeptId())).list();

								for (User memberUser : userList) {
									if (memberUser.getId().equals(
											String.valueOf(userModel
													.getUserId()))) {
										identityService.deleteMembership(
												String.valueOf(userModel
														.getUserId()),
												String.valueOf(departmentModel
														.getDeptId()));
									}
								}
							}

							for (StaffDepartmentModel staffDepartmentModel : existedStaffDepartmentList) {
								DepartmentModel deptModel = staffDepartmentModel
										.getDepartmentModel();
								if (deptModel.getDeptId() == removeDeptId) {
									staffModel.getStaffDepartments().remove(
											staffDepartmentModel);
								}
							}
						}
					}
				}

			} else {
				newDeptIds = selectedDepartmentIds;
			}

			if (newDeptIds != null) {
				LOGGER.info("assign new membership:{} ", newDeptIds.size());
				for (Long newDeptId : newDeptIds) {
					departmentModel = departmentRepository.findOne(newDeptId);
					if (identityService
							.createGroupQuery()
							.groupId(
									String.valueOf(departmentModel.getDeptId()))
							.count() > 0) {
						identityService.createMembership(
								String.valueOf(userModel.getUserId()),
								String.valueOf(departmentModel.getDeptId()));
					}

					StaffDepartmentModel newStaffDepartmentModel = StaffDepartmentModel
							.getBuilder(departmentModel, staffModel, new Date())
							.build();
					staffModel.addStaffDepartment(newStaffDepartmentModel);
				}
			}
		}
		LOGGER.info("assignMembershipOfDepartment end:{} ");
	}
}
