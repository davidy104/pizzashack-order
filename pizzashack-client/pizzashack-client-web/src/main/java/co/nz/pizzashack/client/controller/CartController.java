package co.nz.pizzashack.client.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.nz.pizzashack.client.data.Cart;
import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.ds.PizzashackDS;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CartController.class);

	private static final String REQUEST_PIZZA_INDEX_MAPPING_VIEW = "/index";
	private static final String FEEDBACK_MESSAGE_KEY_PIZZASHACK_ADDED = "feedback.message.pizzashack.added";

	public static final String CART_VIEW = "cart";
	public static final String MODEL_ATTRIBUTE_CART = "cart";

	@Resource
	private Cart cart;

	@Resource
	private PizzashackDS pizzashackDs;

	@RequestMapping(value = "/add/{pizzashackId}/{orderAmount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addToCart(@PathVariable("pizzashackId") Long pizzashackId,
			@PathVariable("orderAmount") Integer orderAmount,
			RedirectAttributes redirect) throws Exception {
		PizzashackDto pizzashack = pizzashackDs
				.getPizzashackDtoById(pizzashackId);
		orderAmount = orderAmount == null ? 0 : orderAmount;
		while (orderAmount > 0) {
			cart.addPizzashack(pizzashack);
			orderAmount--;
		}

		LOGGER.info("Cart: {}", this.cart);
		addFeedbackMessage(redirect, FEEDBACK_MESSAGE_KEY_PIZZASHACK_ADDED,
				pizzashack.getPizzaName());
		return createRedirectViewPath(REQUEST_PIZZA_INDEX_MAPPING_VIEW);
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showCart(Model model) throws Exception {
		LOGGER.info("showCart start: {}");
		LOGGER.info("Cart: {}", cart);
		model.addAttribute(MODEL_ATTRIBUTE_CART, cart);
		return CART_VIEW;
	}

}
