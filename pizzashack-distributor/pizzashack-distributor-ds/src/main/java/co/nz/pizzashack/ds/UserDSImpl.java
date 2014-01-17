package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.UserPredicates.findByUsername;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.DuplicatedException;
import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.RoleModel;
import co.nz.pizzashack.data.model.UserModel;
import co.nz.pizzashack.data.model.UserRoleModel;
import co.nz.pizzashack.data.repository.RoleRepository;
import co.nz.pizzashack.data.repository.UserRepository;
import co.nz.pizzashack.utils.GeneralUtils;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class UserDSImpl implements UserDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDSImpl.class);

	@Resource
	private UserConverter userConverter;

	@Resource
	private UserRepository userRepository;

	@Resource
	private RoleRepository roleRepository;

	@Resource
	private IdentityService identityService;

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public UserDto createUser(String username, String password,
			Set<Long> selectedRoleIds) throws Exception {
		LOGGER.info("createUser start:{}");
		LOGGER.info("username{}", username);
		LOGGER.info("password:{}", password);
		UserDto added = null;
		UserModel userModel = userRepository.findOne(findByUsername(username));
		if (userModel != null
				|| identityService.createUserQuery().userId(username).count() > 0) {
			throw new DuplicatedException("User already existed with name["
					+ username + "]");
		}

		userModel = UserModel.getBuilder(username,
				GeneralUtils.pwdEncode(password)).build();
		userModel.setCreateTime(new Date());

		if (selectedRoleIds != null) {
			for (Long roleId : selectedRoleIds) {
				RoleModel roleModel = roleRepository.findOne(roleId);
				if (roleModel == null) {
					throw new NotFoundException("Role not found by id["
							+ roleId + "]");
				}

				UserRoleModel userRoleModel = UserRoleModel.getBuilder(
						userModel, roleModel, new Date()).build();
				userModel.addUserRole(userRoleModel);
			}
		}

		userModel = userRepository.save(userModel);
		User wfUser = identityService.newUser(String.valueOf(userModel
				.getUserId()));
		identityService.saveUser(wfUser);

		added = userConverter.toDto(userModel);
		LOGGER.info("createUser end:{}", added);
		return added;
	}

	@Override
	public UserDto getUserById(Long userId, boolean loadRoles) throws Exception {
		LOGGER.info("getUserById start:{}", userId);
		UserDto found = null;
		UserModel userModel = userRepository.findOne(userId);
		if (userModel == null) {
			throw new NotFoundException("User not found by id[" + userId + "]");
		}
		found = userConverter.toDto(userModel, loadRoles);
		LOGGER.info("getUserById end:{}", found);
		return found;
	}

	@Override
	public UserDto getUserByName(String username, boolean loadRoles)
			throws Exception {
		LOGGER.info("getUserByName start:{}", username);
		UserDto found = null;
		UserModel userModel = userRepository.findOne(findByUsername(username));
		if (userModel == null) {
			throw new NotFoundException("User not found by username["
					+ username + "]");
		}
		found = userConverter.toDto(userModel, loadRoles);
		LOGGER.info("getUserByName end:{}", found);
		return found;
	}

	@Override
	public Set<UserDto> getAllUsers() throws Exception {
		LOGGER.info("getAllUsers start:{}");
		Set<UserDto> resultSet = null;
		List<UserModel> userModelList = userRepository.findAll();
		if (userModelList != null && userModelList.size() > 0) {
			resultSet = new HashSet<UserDto>();
			for (UserModel userModel : userModelList) {
				resultSet.add(userConverter.toDto(userModel));
			}
		}
		LOGGER.info("getAllUsers end:{}");
		return resultSet;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void updateUser(Long userId, UserDto userDto,
			Set<Long> updatedRoleIds) throws Exception {
		UserModel userModel = userRepository.findOne(userId);
		if (userModel == null) {
			throw new NotFoundException("User not found by id[" + userId + "]");
		}
		if (!StringUtils.isEmpty(userDto.getUsername())) {
			// username is same as the userId of wfuser,
			String originUsername = userModel.getUsername();
			String newUsername = userDto.getUsername();
			userModel.setUsername(newUsername);

			if (identityService.createUserQuery().userId(originUsername)
					.count() > 0) {
				List<Group> groupList = identityService.createGroupQuery()
						.groupMember(originUsername).list();
				if (groupList != null) {
					for (Group group : groupList) {
						identityService.deleteMembership(originUsername,
								group.getId());
						identityService.createMembership(newUsername,
								group.getId());
					}
				}

				identityService.deleteUser(originUsername);
				User wfUser = identityService.newUser(newUsername);
				identityService.saveUser(wfUser);
			}

		}
		if (!StringUtils.isEmpty(userDto.getPassword())) {
			userModel.setPassword(userDto.getPassword());
		}
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void deleteUser(Long userId) throws Exception {
		// TODO Auto-generated method stub

	}

}
