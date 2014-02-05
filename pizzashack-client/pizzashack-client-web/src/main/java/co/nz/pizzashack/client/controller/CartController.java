package co.nz.pizzashack.client.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.nz.pizzashack.client.data.Cart;
import co.nz.pizzashack.client.data.dto.AddToCartRequest;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.ds.PizzashackDS;

import static co.nz.pizzashack.client.controller.OrderController.MODEL_ATTRIBUTE_ORDER;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CartController.class);

	private static final String REQUEST_MAPPING_VIEW_CONFIG = "/pizzashack/{pizzashackId}";
	private static final String PARAMETER_PIZZA_ID = "pizzashackId";
	private static final String FEEDBACK_MESSAGE_KEY_PIZZASHACK_ADDED = "feedback.message.pizzashack.added";

	public static final String CART_VIEW = "cart";
	public static final String MODEL_ATTRIBUTE_CART = "cart";
	public static final String MODEL_ATTRIBUTE_ADDCARTREQ = "addToCartReq";
	public static final String INDICATOR_ATTRIBUTE_PLACEORDER = "readyForOrder";

	@Resource
	private Cart cart;

	@Resource
	private PizzashackDS pizzashackDs;

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addToCart(
			@Valid @ModelAttribute(MODEL_ATTRIBUTE_ADDCARTREQ) AddToCartRequest addToCartRequest,
			RedirectAttributes redirect) throws Exception {
		LOGGER.info("addToCart start:{} ", addToCartRequest);
		Long pizzashackId = addToCartRequest.getPizzashackId();
		Integer orderAmount = addToCartRequest.getAmount();
		PizzashackDto pizzashack = pizzashackDs
				.getPizzashackDtoById(pizzashackId);
		orderAmount = orderAmount == null ? 0 : orderAmount;
		while (orderAmount > 0) {
			cart.addPizzashack(pizzashack);
			orderAmount--;
		}

		LOGGER.info("Cart: {}", this.cart);
		redirect.addAttribute(PARAMETER_PIZZA_ID, pizzashackId);
		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_PIZZASHACK_ADDED,
				pizzashack.getPizzaName());
		return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONFIG);
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showCart(Model model) throws Exception {
		LOGGER.info("showCart start: {}");
		LOGGER.info("Cart: {}", cart);
		if (cart.getPizzashackItems().size() > 0) {
			model.addAttribute(INDICATOR_ATTRIBUTE_PLACEORDER, 1);
		}

		OrderDto orderDto = new OrderDto();
		model.addAttribute(MODEL_ATTRIBUTE_ORDER, orderDto);
		model.addAttribute(MODEL_ATTRIBUTE_CART, cart);
		return CART_VIEW;
	}

}
