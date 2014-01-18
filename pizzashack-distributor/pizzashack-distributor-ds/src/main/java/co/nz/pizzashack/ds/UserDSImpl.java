package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.UserPredicates.findByUsername;
import static co.nz.pizzashack.data.predicates.UserPredicates.findByUsernameAndPassword;

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

import co.nz.pizzashack.AuthenticationException;
import co.nz.pizzashack.DuplicatedException;
import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.UserModel;
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
	private IdentityService identityService;

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public UserDto createUser(String username, String password)
			throws Exception {
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

		userModel = userRepository.save(userModel);
		User wfUser = identityService.newUser(String.valueOf(userModel
				.getUserId()));
		identityService.saveUser(wfUser);

		added = userConverter.toDto(userModel);
		LOGGER.info("createUser end:{}", added);
		return added;
	}

	@Override
	public UserDto loginUser(String username, String password)
			throws AuthenticationException {
		LOGGER.info("loginUser start:{}");
		LOGGER.info("username:{}", username);
		LOGGER.info("password:{}", password);
		UserDto found = null;
		UserModel userModel = userRepository.findOne(findByUsernameAndPassword(
				username, password));
		if (userModel == null) {
			throw new AuthenticationException("User not existed");
		}
		found = userConverter.toDto(userModel);
		LOGGER.info("loginUser end:{}", found);
		return found;
	}

	@Override
	public UserDto getUserById(Long userId) throws Exception {
		LOGGER.info("getUserById start:{}", userId);
		UserDto found = null;
		UserModel userModel = userRepository.findOne(userId);
		if (userModel == null) {
			throw new NotFoundException("User not found by id[" + userId + "]");
		}
		found = userConverter.toDto(userModel);
		LOGGER.info("getUserById end:{}", found);
		return found;
	}

	@Override
	public UserDto getUserByName(String username) throws Exception {
		LOGGER.info("getUserByName start:{}", username);
		UserDto found = null;
		UserModel userModel = userRepository.findOne(findByUsername(username));
		if (userModel == null) {
			throw new NotFoundException("User not found by username["
					+ username + "]");
		}
		found = userConverter.toDto(userModel);
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
	public void updateUser(Long userId, UserDto userDto) throws Exception {
		LOGGER.info("updateUser start:{} ", userDto);
		UserModel userModel = userRepository.findOne(userId);
		if (userModel == null) {
			throw new NotFoundException("User not found by id[" + userId + "]");
		}
		if (!StringUtils.isEmpty(userDto.getUsername())) {
			userModel.setUsername(userDto.getUsername());
		}
		if (!StringUtils.isEmpty(userDto.getPassword())) {
			userModel.setPassword(userDto.getPassword());
		}
		LOGGER.info("updateUser end:{} ", userModel);
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void deleteUser(Long userId) throws Exception {
		LOGGER.info("deleteUser start:{} ", userId);
		UserModel userModel = userRepository.findOne(userId);
		if (userModel == null) {
			throw new NotFoundException("User not found by id[" + userId + "]");
		}

		if (identityService.createUserQuery().userId(String.valueOf(userId))
				.count() > 0) {
			LOGGER.info("user already in identityService");
			List<Group> groupList = identityService.createGroupQuery()
					.groupMember(String.valueOf(userId)).list();

			if (groupList != null) {
				for (Group group : groupList) {
					identityService.deleteMembership(String.valueOf(userId),
							group.getId());
				}
			}
			identityService.deleteUser(String.valueOf(userId));
		}

		userRepository.delete(userModel);
		LOGGER.info("deleteUser end:{} ");
	}

}
