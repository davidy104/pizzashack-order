package co.nz.pizzashack.client.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.ds.PizzashackDS;

@Controller
public class PizzashackController extends BaseController {

	public static final String INDEX_VIEW = "index";
	public static final String SHOW_VIEW = "showPizza";

	public static final String MODEL_ATTRIBUTE_PIZZAS = "pizzas";
	public static final String MODEL_ATTRIBUTE_PIZZA = "pizza";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackController.class);

	@Resource
	private PizzashackDS pizzashackDs;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.debug("index: {}");
		Set<PizzashackDto> pizzaList = pizzashackDs.pizzashackItems();
		model.addAttribute(MODEL_ATTRIBUTE_PIZZAS, pizzaList);
		return INDEX_VIEW;
	}

	@RequestMapping(value = "/pizzashack/{pizzashackId}", method = RequestMethod.GET)
	public String show(@PathVariable("pizzashackId") Long pizzashackId,
			Model model) throws Exception {
		LOGGER.info("show start: {}", pizzashackId);
		PizzashackDto found = pizzashackDs.getPizzashackDtoById(pizzashackId);

		LOGGER.info("Found Pizzashack: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_PIZZA, found);
		return SHOW_VIEW;
	}
}
