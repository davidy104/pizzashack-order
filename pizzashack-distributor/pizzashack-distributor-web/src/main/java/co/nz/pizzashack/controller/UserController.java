package co.nz.pizzashack.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.ds.UserDS;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	public static final String USER_FOLDER = "user/";

	public static final String USER_INDEX_VIEW = USER_FOLDER + "index";
	public static final String CREATE_USER_VIEW = USER_FOLDER + "create";
	public static final String SHOW_USER_VIEW = USER_FOLDER + "show";
	public static final String UPDATE_USER_VIEW = USER_FOLDER + "update";

	public static final String MODEL_ATTRIBUTE_USERS = "users";
	public static final String MODEL_ATTRIBUTE_USER = "user";

	private static final String FEEDBACK_MESSAGE_KEY_USER_CREATED = "feedback.message.user.created";
	private static final String FEEDBACK_MESSAGE_KEY_USER_UPDATED = "feedback.message.user.updated";
	private static final String FEEDBACK_MESSAGE_KEY_USER_DELETED = "feedback.message.user.deleted";

	private static final String REQUEST_USERS_INDEX_MAPPING_VIEW = "/user/list";
	private static final String REQUEST_USER_MAPPING_VIEW = "/user/{userId}";

	private static final String PARAMETER_USER_ID = "userId";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);

	@Resource
	private UserDS userDs;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.info("userIndex start: {}");
		Set<UserDto> users = userDs.getAllUsers(null);
		if (users != null) {
			LOGGER.info("users size: {}", users.size());
		}

		model.addAttribute(MODEL_ATTRIBUTE_USERS, users);
		model.addAttribute(MODEL_ATTRIBUTE_USER, new UserDto());
		return USER_INDEX_VIEW;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute(MODEL_ATTRIBUTE_USER) UserDto userDto,
			Model model) throws Exception {
		LOGGER.info("search start:{}");
		Set<UserDto> users = userDs.getAllUsers(userDto);
		model.addAttribute(MODEL_ATTRIBUTE_USERS, users);
		return USER_INDEX_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreate(Model model) throws Exception {
		LOGGER.info("showCreate start: {}");
		model.addAttribute(MODEL_ATTRIBUTE_USER, new UserDto());
		return CREATE_USER_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_USER) UserDto userDto,
			BindingResult result, RedirectAttributes redirect) throws Exception {
		LOGGER.info("createUser start:{}", userDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return CREATE_USER_VIEW;
		}

		UserDto addedDto = userDs.createUser(userDto.getUsername(),
				userDto.getPassword());

		LOGGER.info("result: {}", addedDto);

		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_USER_CREATED,
				addedDto.getUserId());
		return createRedirectViewPath(REQUEST_USERS_INDEX_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String show(@PathVariable("userId") Long userId, Model model)
			throws Exception {
		LOGGER.info("showUser start: {}", userId);
		UserDto found = userDs.getUserById(userId);

		LOGGER.info("Found user: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_USER, found);
		return SHOW_USER_VIEW;
	}

	@RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
	public String showUpdate(@PathVariable("userId") Long userId, Model model)
			throws Exception {
		LOGGER.info("showUpdateUser start: {}");
		UserDto found = userDs.getUserById(userId);
		LOGGER.info("Found user: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_USER, found);
		return UPDATE_USER_VIEW;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_USER) UserDto userDto,
			BindingResult result, RedirectAttributes attributes)
			throws Exception {
		LOGGER.info("updateUser start: {}", userDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return UPDATE_USER_VIEW;
		}

		userDs.updateUser(userDto.getUserId(), userDto);

		attributes.addAttribute(PARAMETER_USER_ID, userDto.getUserId());
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_USER_UPDATED,
				userDto.getUserId());
		return createRedirectViewPath(REQUEST_USER_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUser(@PathVariable("userId") Long userId)
			throws Exception {
		LOGGER.info("deleteUser: {}", userId);
		userDs.deleteUser(userId);
		return getMessage(FEEDBACK_MESSAGE_KEY_USER_DELETED, userId);
	}

}
