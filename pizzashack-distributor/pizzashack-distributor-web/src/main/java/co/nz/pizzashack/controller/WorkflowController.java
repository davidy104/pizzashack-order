package co.nz.pizzashack.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.nz.pizzashack.data.dto.WorkflowDto;
import co.nz.pizzashack.ds.WorkflowDS;

@Controller
@RequestMapping("/workflow")
public class WorkflowController extends BaseController {

	public static final String WORKFLOW_FOLDER = "workflow/";

	public static final String WORKFLOW_INDEX_VIEW = WORKFLOW_FOLDER + "index";
	public static final String WORKFLOW_SHOW_VIEW = WORKFLOW_FOLDER + "show";

	public static final String MODEL_ATTRIBUTE_WORKFLOWS = "workflows";
	public static final String MODEL_ATTRIBUTE_WORKFLOW = "workflow";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WorkflowController.class);

	@Resource
	private WorkflowDS workflowDs;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.info("index start: {}");
		Set<WorkflowDto> workflows = workflowDs.getAllWorkflows();
		model.addAttribute(MODEL_ATTRIBUTE_WORKFLOWS, workflows);
		model.addAttribute(MODEL_ATTRIBUTE_WORKFLOW, new WorkflowDto());
		LOGGER.info("index end:{} ");
		return WORKFLOW_INDEX_VIEW;
	}

	@RequestMapping(value = "/{workflowId}", method = RequestMethod.GET)
	public String show(@PathVariable("workflowId") Long workflowId, Model model)
			throws Exception {
		LOGGER.info("show start: {}", workflowId);
		WorkflowDto found = workflowDs.getWorkflowById(workflowId);
		model.addAttribute(MODEL_ATTRIBUTE_WORKFLOW, found);
		return WORKFLOW_SHOW_VIEW;
	}

	@RequestMapping(value = "/doShowImage/{workflowId}", method = RequestMethod.GET)
	public void doShowImage(final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("workflowId") Long workflowId) {
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		try {
			byte[] data = workflowDs.getFLowImageBytes(workflowId, realPath);
			response.setContentType("image/png");
			FileCopyUtils.copy(data, response.getOutputStream());

		} catch (Exception e) {
			LOGGER.info("output image error:{} ", e);
		}
	}

	@RequestMapping(value = "/showImage/{workflowId}")
	@ResponseBody
	public String showImage(@PathVariable("workflowId") Long workflowId)
			throws Exception {
		return "<img src='http://localhost:8111/workflow/doShowImage/"
				+ workflowId + "/>";
	}

}
