package co.nz.pizzashack.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.nz.pizzashack.data.dto.DepartmentDto;
import co.nz.pizzashack.ds.DepartmentDS;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

	public static final String DEPT_FOLDER = "dept/";

	public static final String DEPT_INDEX_VIEW = DEPT_FOLDER + "index";
	public static final String CREATE_DEPT_VIEW = DEPT_FOLDER + "create";
	public static final String SHOW_DEPT_VIEW = DEPT_FOLDER + "show";
	public static final String UPDATE_DEPT_VIEW = DEPT_FOLDER + "update";
	public static final String SELECT_DEPT_VIEW = DEPT_FOLDER + "selectDepts";

	public static final String MODEL_ATTRIBUTE_DEPTS = "depts";
	public static final String MODEL_ATTRIBUTE_DEPT = "dept";

	private static final String FEEDBACK_MESSAGE_KEY_DEPT_CREATED = "feedback.message.dept.created";
	private static final String FEEDBACK_MESSAGE_KEY_DEPT_UPDATED = "feedback.message.dept.updated";
	private static final String FEEDBACK_MESSAGE_KEY_DEPT_DELETED = "feedback.message.dept.deleted";

	private static final String REQUEST_DEPT_INDEX_MAPPING_VIEW = "/department/list";
	private static final String REQUEST_DEPT_MAPPING_VIEW = "/department/{deptId}";

	private static final String PARAMETER_DEPT_ID = "deptId";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DepartmentController.class);

	@Resource
	private DepartmentDS departmentDs;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.info("departmentIndex start: {}");
		Set<DepartmentDto> depts = departmentDs.getAllDepartments(null);
		if (depts != null) {
			LOGGER.info("depts size: {}", depts.size());
		}

		model.addAttribute(MODEL_ATTRIBUTE_DEPTS, depts);
		model.addAttribute(MODEL_ATTRIBUTE_DEPT, new DepartmentDto());
		return DEPT_INDEX_VIEW;
	}

	@RequestMapping(value = "/selectdepts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Set<DepartmentDto> select() throws Exception {
		LOGGER.info("select start: {}");
		Set<DepartmentDto> depts = departmentDs.getAllDepartments(null);
		if (depts != null) {
			LOGGER.info("depts size: {}", depts.size());
		}
		return depts;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute(MODEL_ATTRIBUTE_DEPT) DepartmentDto departmentDto,
			BindingResult result, Model model) throws Exception {
		LOGGER.info("search users start:{}");
		Set<DepartmentDto> depts = departmentDs
				.getAllDepartments(departmentDto);
		model.addAttribute(MODEL_ATTRIBUTE_DEPTS, depts);
		return DEPT_INDEX_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreate(Model model) throws Exception {
		LOGGER.info("showCreateUser start: {}");
		model.addAttribute(MODEL_ATTRIBUTE_DEPT, new DepartmentDto());
		return CREATE_DEPT_VIEW;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_DEPT) DepartmentDto departmentDto,
			BindingResult result, RedirectAttributes redirect) throws Exception {
		LOGGER.info("create start:{}", departmentDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return CREATE_DEPT_VIEW;
		}
		DepartmentDto added = departmentDs.createDepartment(departmentDto);
		LOGGER.info("result: {}", added);

		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_DEPT_CREATED,
				added.getDeptName());
		return createRedirectViewPath(REQUEST_DEPT_INDEX_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{deptId}", method = RequestMethod.GET)
	public String show(@PathVariable("deptId") Long deptId, Model model)
			throws Exception {
		LOGGER.info("show start: {}", deptId);
		DepartmentDto found = departmentDs.getDepartmentDtoById(deptId);

		LOGGER.info("Found dept: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_DEPT, found);
		return SHOW_DEPT_VIEW;
	}

	@RequestMapping(value = "/update/{deptId}", method = RequestMethod.GET)
	public String showUpdate(@PathVariable("deptId") Long deptId, Model model)
			throws Exception {
		LOGGER.info("showUpdate start: {}");
		DepartmentDto found = departmentDs.getDepartmentDtoById(deptId);
		LOGGER.info("Found: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_DEPT, found);
		return UPDATE_DEPT_VIEW;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_DEPT) DepartmentDto departmentDto,
			BindingResult result, RedirectAttributes attributes)
			throws Exception {
		LOGGER.info("update start: {}", departmentDto);

		if (result.hasErrors()) {
			LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
			return UPDATE_DEPT_VIEW;
		}
		departmentDs.updateDepartmentName(departmentDto.getDeptId(),
				departmentDto.getDeptName());

		attributes.addAttribute(PARAMETER_DEPT_ID, departmentDto.getDeptId());
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_DEPT_UPDATED,
				departmentDto.getDeptId());
		return createRedirectViewPath(REQUEST_DEPT_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{deptId}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("deptId") Long deptId) throws Exception {
		LOGGER.info("delete: {}", deptId);
		departmentDs.deleteDepartment(deptId);
		return getMessage(FEEDBACK_MESSAGE_KEY_DEPT_DELETED, deptId);
	}

}
