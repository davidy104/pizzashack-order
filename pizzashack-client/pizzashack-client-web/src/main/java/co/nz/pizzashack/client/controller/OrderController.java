package co.nz.pizzashack.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.nz.pizzashack.client.data.Cart;
import co.nz.pizzashack.client.data.dto.BillingDto;
import co.nz.pizzashack.client.data.dto.BillingResp;
import co.nz.pizzashack.client.data.dto.OrderDetailsDto;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.ds.OrderDS;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderController.class);

	public static final String ORDER_FOLDER = "order/";

	public static final String ORDER_INDEX_VIEW = ORDER_FOLDER + "index";
	public static final String ORDER_PLACE_VIEW = ORDER_FOLDER + "place";
	public static final String ORDER_SHOW_VIEW = ORDER_FOLDER + "show";
	public static final String BILLING_SHOW_VIEW = ORDER_FOLDER + "billing";

	public static final String MODEL_ATTRIBUTE_ORDERS = "orders";
	public static final String MODEL_ATTRIBUTE_ORDER = "order";
	public static final String MODEL_ATTRIBUTE_BILLING = "billing";
	public static final String MODEL_ATTRIBUTE_PAYMODES = "paymodes";
	

	public static final String INDICATOR_ATTRIBUTE_BILLING = "pendingOnBilling";

	private static final String REQUEST_ORDER_MAPPING_VIEW = "/order/{orderNo}";
	private static final String PARAMETER_ORDER_NO = "orderNo";

	private static final String FEEDBACK_MESSAGE_KEY_ORDER_PLACED = "feedback.message.order.placed";
	private static final String FEEDBACK_MESSAGE_KEY_BILLING_SUCCESS = "feedback.message.order.billing.success";
	private static final String FEEDBACK_MESSAGE_KEY_BILLING_FAILED = "feedback.message.order.billing.failed";

	@Resource
	private Cart cart;

	@Resource
	private OrderDS orderDs;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.info("index start: {}");
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, new OrderDto());
		return ORDER_INDEX_VIEW;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String search(
			@ModelAttribute(MODEL_ATTRIBUTE_ORDER) OrderDto orderDto,
			Model model) throws Exception {
		LOGGER.info("search start:{}");
		Set<OrderDto> orders = null;
		String email = orderDto.getCustomer().getCustomerEmail();
		if (!StringUtils.isEmpty(email)) {
			orders = orderDs.getOrdersByCustomer(email);
		}
		model.addAttribute(MODEL_ATTRIBUTE_ORDERS, orders);
		return ORDER_INDEX_VIEW;
	}

	@RequestMapping(value = "/billing", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String billing(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_BILLING) BillingDto billing,
			BindingResult result, RedirectAttributes redirect) throws Exception {
		BillingResp billingResp = orderDs.payForOrder(billing);
		String billingCode = billingResp.getBillingCode();

		if (billingCode.equals("000")) {
			addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_BILLING_SUCCESS,
					billingResp.getOrderNo());
		} else {
			addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_BILLING_FAILED,
					billingResp.getOrderNo(), billingResp.getBillingMessage());
		}

		redirect.addAttribute(PARAMETER_ORDER_NO, billingResp.getOrderNo());
		return createRedirectViewPath(REQUEST_ORDER_MAPPING_VIEW);
	}

	@RequestMapping(value = "/{orderNo}", method = RequestMethod.GET)
	public String show(@PathVariable("orderNo") String orderNo, Model model)
			throws Exception {
		LOGGER.info("show start: {}", orderNo);
		OrderDto found = orderDs.getOrderByNo(orderNo);
		BillingDto billing = new BillingDto();
		LOGGER.info("Found dept: {}", found);
		String status = found.getStatus();
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, found);
		
		List<String> paymodes = new ArrayList<String>();
		paymodes.add("credit");
		paymodes.add("debit");
		model.addAttribute(MODEL_ATTRIBUTE_PAYMODES, paymodes);
		
		
		if (status.equals("dataEntry")) {
			// to handle after service side ready
		} else if (status.equals("pendingOnBilling")) {
			model.addAttribute(INDICATOR_ATTRIBUTE_BILLING, 1);
			billing.setOrderNo(orderNo);
			billing.setBillingAmount(found.getTotalPrice());
		}
		model.addAttribute(MODEL_ATTRIBUTE_BILLING, billing);
		return ORDER_SHOW_VIEW;
	}

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String placeOrder(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_ORDER) OrderDto orderDto,
			RedirectAttributes redirect) throws Exception {
		LOGGER.info("create start:{}", orderDto);
		OrderDto orderResponse = null;

		Map<PizzashackDto, Integer> items = cart.getPizzashackItems();
		for (Map.Entry<PizzashackDto, Integer> entry : items.entrySet()) {
			PizzashackDto pizzashackDto = entry.getKey();
			OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
			orderDetailsDto.setPizzaName(pizzashackDto.getPizzaName());
			orderDetailsDto.setQty(entry.getValue());
			orderDto.addOrderDetails(orderDetailsDto);
		}

		orderResponse = orderDs.placeOrder(orderDto);
		cart.clear();
		LOGGER.info("result: {}", orderResponse);
		redirect.addAttribute(PARAMETER_ORDER_NO, orderResponse.getOrderNo());
		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_ORDER_PLACED,
				orderResponse.getOrderNo());
		return createRedirectViewPath(REQUEST_ORDER_MAPPING_VIEW);
	}
}
