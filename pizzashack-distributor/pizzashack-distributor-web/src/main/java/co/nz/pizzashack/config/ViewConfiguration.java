package co.nz.pizzashack.config;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@PropertySource({ "classpath:messagesource.properties" })
public class ViewConfiguration {
	private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";

	private static final String VIEW_RESOLVER_SUFFIX = ".jsp";

	private static final String PROPERTY_NAME_MESSAGESOURCE_BASENAME = "message.source.basename";

	private static final String PROPERTY_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE = "message.source.use.code.as.default.message";

	@Resource
	private Environment environment;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
		viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
		return viewResolver;
	}

	/**
	 * To resolve message codes to actual messages we need a
	 * {@link MessageSource} implementation. The default implementations use a
	 * {@link java.util.ResourceBundle} to parse the property files with the
	 * messages in it.
	 * 
	 * @return the {@link MessageSource}
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasename(environment
				.getRequiredProperty(PROPERTY_NAME_MESSAGESOURCE_BASENAME));
		messageSource
				.setUseCodeAsDefaultMessage(Boolean.parseBoolean(environment
						.getRequiredProperty(PROPERTY_NAME_MESSAGESOURCE_USE_CODE_AS_DEFAULT_MESSAGE)));

		return messageSource;
	}
}
