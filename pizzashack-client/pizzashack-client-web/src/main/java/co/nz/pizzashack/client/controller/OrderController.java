package co.nz.pizzashack.client.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.ds.OrderDS;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderController.class);

	public static final String ORDER_FOLDER = "order/";

	public static final String ORDER_INDEX_VIEW = ORDER_FOLDER + "index";
	public static final String ORDER_PLACE_VIEW = ORDER_FOLDER + "place";

	public static final String MODEL_ATTRIBUTE_ORDERS = "orders";
	public static final String MODEL_ATTRIBUTE_ORDER = "order";

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

	@RequestMapping(value = "/placeOrder", method = RequestMethod.GET)
	public String showPlaceOrder(Model model) throws Exception {
		LOGGER.info("showPlaceOrder start: {}");
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, new OrderDto());
		return ORDER_PLACE_VIEW;
	}
}
