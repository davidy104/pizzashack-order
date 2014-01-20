package co.nz.pizzashack.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.drools.core.util.StringUtils;
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

import co.nz.pizzashack.data.StaffLoadStrategies;
import co.nz.pizzashack.data.dto.DepartmentDto;
import co.nz.pizzashack.data.dto.StaffDto;
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

		List<String> levels = new ArrayList<String>();
		levels.add("senior");
		levels.add("intermedior");
		levels.add("junior");

		model.addAttribute(MODEL_ATTRIBUTE_STAFF, new StaffDto());
		model.addAttribute("roles", roles);
		model.addAttribute("levels", levels);
		return CREATE_STAFF_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_STAFF) StaffDto staffDto,
			BindingResult result, RedirectAttributes redirect) throws Exception {
		LOGGER.info("create start:{}", staffDto);
		Set<Long> deptIds = null;

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return CREATE_STAFF_VIEW;
		}
		String selectedDeptIdsStr = staffDto.getSelectedDeptIdsStr();
		if (!StringUtils.isEmpty(selectedDeptIdsStr)) {
			LOGGER.info("selectedDeptIdsStr:{} ", selectedDeptIdsStr);
			String[] idsArray = selectedDeptIdsStr.split(",");
			deptIds = new HashSet<Long>();
			for (String idStr : idsArray) {
				deptIds.add(Long.valueOf(idStr));
			}

		}

		StaffDto addedDto = staffDs.createStaff(staffDto, deptIds);
		LOGGER.info("result: {}", addedDto);
		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_STAFF_CREATED,
				addedDto.getStaffId());
		return createRedirectViewPath(REQUEST_STAFF_INDEX_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{staffId}", method = RequestMethod.GET)
	public String show(@PathVariable("staffId") Long staffId, Model model)
			throws Exception {
		LOGGER.info("show start: {}", staffId);
		StaffDto found = staffDs.getStaffById(staffId,
				StaffLoadStrategies.LOAD_ALL);
		StringBuilder sb = new StringBuilder();
		if (found != null && found.getDepartments() != null) {
			for (DepartmentDto dept : found.getDepartments()) {
				sb.append(dept.getDeptName()).append(",");
			}
			found.setSelectedDeptIdsStr(sb.toString());
		}

		LOGGER.info("Found dept: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_STAFF, found);
		return SHOW_STAFF_VIEW;
	}

	@RequestMapping(value = "/update/{staffId}", method = RequestMethod.GET)
	public String showUpdate(@PathVariable("staffId") Long staffId, Model model)
			throws Exception {
		LOGGER.info("showUpdate start: {}");
		StaffDto found = staffDs.getStaffById(staffId,
				StaffLoadStrategies.LOAD_ALL);
		LOGGER.info("Found: {}", found);

		StringBuilder idsSb = new StringBuilder();
		StringBuilder namesSb = new StringBuilder();

		if (found != null && found.getDepartments() != null) {
			for (DepartmentDto dept : found.getDepartments()) {
				idsSb.append(dept.getDeptName()).append(",");
				namesSb.append(dept.getDeptName()).append(",");
			}
			found.setSelectedDeptIdsStr(idsSb.toString());
			found.setSelectedDeptNamesStr(namesSb.toString());
		}

		List<String> roles = new ArrayList<String>();
		roles.add("operator");
		roles.add("manager");

		List<String> levels = new ArrayList<String>();
		levels.add("senior");
		levels.add("intermedior");
		levels.add("junior");

		model.addAttribute("roles", roles);
		model.addAttribute("levels", levels);
		model.addAttribute(MODEL_ATTRIBUTE_STAFF, found);
		return UPDATE_STAFF_VIEW;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_STAFF) StaffDto staffDto,
			BindingResult result, RedirectAttributes attributes)
			throws Exception {
		LOGGER.info("update start: {}", staffDto);
		Set<Long> deptIds = null;
		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return UPDATE_STAFF_VIEW;
		}

		String selectedDeptIdsStr = staffDto.getSelectedDeptIdsStr();
		if (!StringUtils.isEmpty(selectedDeptIdsStr)) {
			LOGGER.info("selectedDeptIdsStr:{} ", selectedDeptIdsStr);
			String[] idsArray = selectedDeptIdsStr.split(",");
			deptIds = new HashSet<Long>();
			for (String idStr : idsArray) {
				deptIds.add(Long.valueOf(idStr));
			}

		}
		staffDs.updateStaff(staffDto.getStaffId(), staffDto, deptIds);
		attributes.addAttribute(PARAMETER_STAFF_ID, staffDto.getStaffId());
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_STAFF_UPDATED,
				staffDto.getStaffId());
		return createRedirectViewPath(REQUEST_STAFF_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{staffId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("staffId") Long staffId)
			throws Exception {
		LOGGER.info("delete start:{} ", staffId);
		staffDs.deleteStaff(staffId);
		return getMessage(FEEDBACK_MESSAGE_KEY_STAFF_DELETED, staffId);
	}
}
