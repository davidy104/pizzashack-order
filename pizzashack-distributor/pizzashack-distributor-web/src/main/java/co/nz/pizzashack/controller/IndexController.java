package co.nz.pizzashack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.support.SessionAttribute;

@Controller
@RequestMapping("/index")
@SessionAttributes(types = UserDto.class)
public class IndexController extends BaseController {

	public static final String INDEX_VIEW = "index";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IndexController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index(
			Model model,
			@SessionAttribute(value = LoginController.MODEL_ATTRIBUTE_USER, exposeAsModelAttribute = true) UserDto user)
			throws Exception {
		LOGGER.info("index: {}");

		return INDEX_VIEW;
	}
}
