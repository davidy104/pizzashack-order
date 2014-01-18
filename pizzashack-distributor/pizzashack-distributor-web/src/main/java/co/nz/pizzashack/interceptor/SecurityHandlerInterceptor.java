package co.nz.pizzashack.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import co.nz.pizzashack.AuthenticationException;
import co.nz.pizzashack.controller.LoginController;
import co.nz.pizzashack.data.dto.UserDto;

public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserDto user = (UserDto) WebUtils.getSessionAttribute(request,
				LoginController.MODEL_ATTRIBUTE_USER);

		if (user == null) {
			// Retrieve and store the original URL.
			String url = request.getRequestURL().toString();
			WebUtils.setSessionAttribute(request,
					LoginController.REQUESTED_URL, url);
			throw new AuthenticationException("Authentication required.",
					"authentication.required");
		}
		return true;
	}

}
