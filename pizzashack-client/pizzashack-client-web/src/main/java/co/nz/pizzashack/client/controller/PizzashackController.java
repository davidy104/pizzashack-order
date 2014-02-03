package co.nz.pizzashack.client.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.ds.PizzashackDS;

@Controller
@RequestMapping("/index")
public class PizzashackController extends BaseController {

	public static final String INDEX_VIEW = "index";

	protected static final String MODEL_ATTRIBUTE_PIZZAS = "pizzas";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackController.class);

	@Resource
	private PizzashackDS pizzashackDs;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.debug("index: {}");

		Set<PizzashackDto> pizzaList = pizzashackDs.pizzashackItems();
		model.addAttribute(MODEL_ATTRIBUTE_PIZZAS, pizzaList);
		return INDEX_VIEW;
	}
}
