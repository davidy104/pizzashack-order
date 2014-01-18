package co.nz.pizzashack.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return createRedirectViewPath(LoginController.REQUEST_MAPPING_VIEW_CONFIG);
	}

}
