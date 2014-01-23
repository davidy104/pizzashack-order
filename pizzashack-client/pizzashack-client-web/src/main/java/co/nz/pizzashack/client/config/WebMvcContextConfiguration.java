package co.nz.pizzashack.client.config;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import co.nz.pizzashack.client.data.Cart;
import co.nz.pizzashack.client.support.SessionAttributeProcessor;

@Configuration
@EnableWebMvc
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"classpath:/META-INF/web-resources/static/");
	}

	@Override
	public void configureDefaultServletHandling(
			final DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public SessionAttributeProcessor sessionAttributeProcessor() {
		return new SessionAttributeProcessor();
	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(sessionAttributeProcessor());
	}

	// -- Start Locale Support (I18N) --//

	/**
	 * The {@link LocaleChangeInterceptor} allows for the locale to be changed.
	 * It provides a <code>paramName</code> property which sets the request
	 * parameter to check for changing the language, the default is
	 * <code>locale</code>.
	 *
	 * @return the {@link LocaleChangeInterceptor}
	 */
	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 * The {@link LocaleResolver} implementation to use. Specifies where to
	 * store the current selected locale.
	 *
	 * @return the {@link LocaleResolver}
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	// -- End Locale Support (I18N) --//

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(simpleMappingExceptionResolver());
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("AuthenticationException", "login");

		Properties statusCodes = new Properties();
		mappings.setProperty("login",
				String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));

		exceptionResolver.setExceptionMappings(mappings);
		exceptionResolver.setStatusCodes(statusCodes);
		return exceptionResolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean()
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Cart cart() {
		return new Cart();
	}

}
