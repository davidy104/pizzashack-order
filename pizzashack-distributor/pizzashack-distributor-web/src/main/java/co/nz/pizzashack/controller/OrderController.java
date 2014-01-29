package co.nz.pizzashack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.ds.OrderProcessDS;
import co.nz.pizzashack.ds.OrderProcessQueryDS;

@Controller
@RequestMapping("/orderProcess")
public class OrderController extends BaseController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderController.class);

	public static final String ORDER_FOLDER = "order/";

	public static final String SHOW_ORDERPROCESS_VIEW = ORDER_FOLDER
			+ "showOrderProcess";

	public static final String MODEL_ATTRIBUTE_ORDERPROCESS = "orderProcess";
	public static final String MODEL_ATTRIBUTE_ORDERPROCESSES = "orderProcesses";
	public static final String MODEL_ATTRIBUTE_REVIEW = "review";

	public static final String ATTRIBUTE_REVIEWTASK_OPTIONS = "reviewTaskOptions";
	public static final String ATTRIBUTE_CANDIDATE_USERS = "candidateUsers";
	public static final String ATTRIBUTE_CANDIDATE_GROUPS = "candidateGroups";

	private static final String PARAMETER_ORDERPROCESS_ID = "orderProcessId";
	private static final String REQUEST_MAPPING_VIEW_CONFIG = "/orderProcess/{orderProcessId}";
	private static final String FEEDBACK_MESSAGE_KEY_REVIEWTASK_CLAIMED = "feedback.message.orderProcess.review.claimed";
	private static final String FEEDBACK_MESSAGE_KEY_REVIEWTASK_DONE = "feedback.message.orderProcess.review.done";

	public static final String REVIEW_TASK_ACTIVITY = "Manual underwriting";

	@Resource
	private OrderProcessDS orderProcessDs;

	@Resource
	private OrderProcessQueryDS orderProcessQueryDs;

	@RequestMapping(value = "/{orderProcessId}", method = RequestMethod.GET)
	public String showOrderProcess(
			@PathVariable("orderProcessId") Long orderProcessId, Model model,
			HttpSession session) throws Exception {
		LOGGER.debug("showOrderProcess start:{}", orderProcessId);
		UserDto loginUser = (UserDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_USER);
		// 0--to claim; 1-- to review
		Integer reviewTaskOptions = 0;
		OrderProcessDto found = orderProcessQueryDs
				.getOrderProcessDtoById(orderProcessId);
		ProcessActivityDto pendingActivity = found.getPendingActivity();
		String orderNo = found.getOrder().getOrderNo();
		if (pendingActivity.getName().equals(REVIEW_TASK_ACTIVITY)) {
			if (!StringUtils.isEmpty(pendingActivity.getAssignee())) {
				reviewTaskOptions = 1;
			} else {
				Set<String> candidateGroups = pendingActivity
						.getCandidateGroups();
				Set<String> candidateUsers = pendingActivity
						.getCandidateUsers();

				model.addAttribute(ATTRIBUTE_CANDIDATE_USERS,
						this.candidateDisplayConvert(candidateUsers));
				model.addAttribute(ATTRIBUTE_CANDIDATE_GROUPS,
						this.candidateDisplayConvert(candidateGroups));
			}

			if (this.orderProcessDs.ifCurrentUserHasRightForTask(orderNo,
					pendingActivity.getName(), loginUser)) {
				model.addAttribute(ATTRIBUTE_REVIEWTASK_OPTIONS,
						reviewTaskOptions);
			}
		}

		LOGGER.debug("Found order: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_ORDERPROCESS, found);

		OrderReviewRecordDto orderReviewRecordDto = new OrderReviewRecordDto();
		orderReviewRecordDto.setOrderNo(orderNo);
		model.addAttribute(MODEL_ATTRIBUTE_REVIEW, orderReviewRecordDto);
		List<String> reviewOptions = new ArrayList<String>();
		reviewOptions.add("accept");
		reviewOptions.add("reject");
		reviewOptions.add("pending");
		model.addAttribute("reviewResult", reviewOptions);

		return SHOW_ORDERPROCESS_VIEW;
	}

	@RequestMapping(value = "/claimReview/{orderNo}", method = RequestMethod.GET)
	public String claimReviewTask(@PathVariable("orderNo") String orderNo,
			BindingResult result, RedirectAttributes attributes,
			HttpSession session) throws Exception {
		LOGGER.info("claimReviewTask start:{} ");
		UserDto loginUser = (UserDto) session
				.getAttribute(LoginController.MODEL_ATTRIBUTE_USER);
		Long orderProcessId = orderProcessDs.claimOrderReviewTask(orderNo,
				loginUser);
		attributes.addAttribute(PARAMETER_ORDERPROCESS_ID, orderProcessId);
		addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_REVIEWTASK_CLAIMED,
				REVIEW_TASK_ACTIVITY);

		return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONFIG);
	}

	@RequestMapping(value = "/review/{orderNo}", method = RequestMethod.POST)
	@ResponseBody
	public String reviewOrder(
			@PathVariable("orderNo") String orderNo,
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_REVIEW) OrderReviewRecordDto orderReviewRecordDto)
			throws Exception {
		LOGGER.info("reviewOrder start: {}");
		LOGGER.info("orderNo: {}", orderNo);
		orderProcessDs.manualOrderReview(orderNo, orderReviewRecordDto);
		return getMessage(FEEDBACK_MESSAGE_KEY_REVIEWTASK_DONE,
				REVIEW_TASK_ACTIVITY);
	}

	private String candidateDisplayConvert(Set<String> candidates) {
		StringBuilder candidateBuilder = new StringBuilder();

		if (candidates != null) {
			int size = candidates.size();
			int count = 0;
			for (String candidate : candidates) {
				if (count == size) {
					candidateBuilder.append(candidate);
				} else {
					candidateBuilder.append(candidate).append(",");
				}
				count++;
			}
		}
		return candidateBuilder.toString();
	}

}
