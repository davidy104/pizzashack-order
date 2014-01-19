package co.nz.pizzashack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.nz.pizzashack.data.dto.StaffDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.ds.StaffDS;

@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {
	public static final String STAFF_FOLDER = "staff/";

	public static final String STAFF_INDEX_VIEW = STAFF_FOLDER + "index";
	public static final String CREATE_STAFF_VIEW = STAFF_FOLDER + "create";
	public static final String SHOW_STAFF_VIEW = STAFF_FOLDER + "show";
	public static final String UPDATE_STAFF_VIEW = STAFF_FOLDER + "update";

	public static final String MODEL_ATTRIBUTE_STAFFS = "staffs";
	public static final String MODEL_ATTRIBUTE_STAFF = "staff";

	private static final String FEEDBACK_MESSAGE_KEY_STAFF_CREATED = "feedback.message.staff.created";
	private static final String FEEDBACK_MESSAGE_KEY_STAFF_UPDATED = "feedback.message.staff.updated";
	private static final String FEEDBACK_MESSAGE_KEY_STAFF_DELETED = "feedback.message.staff.deleted";

	private static final String REQUEST_STAFF_INDEX_MAPPING_VIEW = "/staff/list";
	private static final String REQUEST_STAFF_MAPPING_VIEW = "/staff/{staffId}";

	private static final String PARAMETER_STAFF_ID = "staffId";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StaffController.class);

	@Resource
	private StaffDS staffDs;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.info("index start: {}");

		Set<StaffDto> staffs = staffDs.getAllStaffs(null);
		if (staffs != null) {
			LOGGER.info("staffs size: {}", staffs.size());
		}

		model.addAttribute(MODEL_ATTRIBUTE_STAFFS, staffs);
		model.addAttribute(MODEL_ATTRIBUTE_STAFF, new StaffDto());
		return STAFF_INDEX_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreate(Model model) throws Exception {
		LOGGER.info("showCreate start: {}");
		List<String> roles = new ArrayList<String>();
		roles.add("operator");
		roles.add("manager");
		model.addAttribute(UserController.MODEL_ATTRIBUTE_USER, new UserDto());
		model.addAttribute(MODEL_ATTRIBUTE_STAFF, new StaffDto());
		model.addAttribute("roles", roles);
		return CREATE_STAFF_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_STAFF) StaffDto staffDto,
			BindingResult result, RedirectAttributes redirect) throws Exception {
		LOGGER.info("create start:{}", staffDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return CREATE_STAFF_VIEW;
		}

		StaffDto addedDto = staffDs.createStaff(staffDto, null);
		LOGGER.info("result: {}", addedDto);
		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_STAFF_CREATED,
				addedDto.getStaffId());
		return createRedirectViewPath(REQUEST_STAFF_INDEX_MAPPING_VIEW);
	}
}
