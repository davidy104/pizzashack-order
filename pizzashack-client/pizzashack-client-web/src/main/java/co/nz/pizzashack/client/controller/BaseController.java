package co.nz.pizzashack.client.controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BaseController {

	protected static final String FLASH_MESSAGE_KEY_ERROR = "errorMessage";

	protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";

	@Resource
	protected MessageSource messageSource;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseController.class);

	protected void addFeedbackMessage(RedirectAttributes model, String code,
			Object... params) {
		LOGGER.info("Adding feedback message with code: {} and params: {}",
				code, params);
		String localizedFeedbackMessage = getMessage(code, params);
		LOGGER.info("Localized message is: {}", localizedFeedbackMessage);
		model.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK,
				localizedFeedbackMessage);
	}

	protected String getMessage(String code, Object... params) {
		Locale current = LocaleContextHolder.getLocale();
		LOGGER.info("Current locale is {}", current);
		return messageSource.getMessage(code, params, current);
	}

	protected String createRedirectViewPath(String requestMapping) {
		LOGGER.info("requestMapping:{}", requestMapping);
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		return redirectViewPath.toString();
	}
}
