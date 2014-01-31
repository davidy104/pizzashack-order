package co.nz.pizzashack.config;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import co.nz.pizzashack.integration.ws.BillingProcessWebServices;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-extension-soap.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml" })
public class CamelCxfConfig {

	// @Bean
	// public CxfEndpoint billingAccountEndpoint() {
	// CxfEndpoint cxfEndpoint = new CxfEndpoint();
	// cxfEndpoint
	// .setAddress("http://localhost:8112/pizzashackbilling/ws/accountWs");
	// cxfEndpoint.setServiceClass(BillingWebServices.class);
	// cxfEndpoint.getOutInterceptors().add(loggingOutInterceptor());
	// cxfEndpoint.setMtomEnabled(true);
	// return cxfEndpoint;
	// }

	@Bean
	public CxfEndpoint billingInboundEndpoint() {
		CxfEndpoint cxfEndpoint = new CxfEndpoint();
		cxfEndpoint.setAddress("/billingWs");
		cxfEndpoint.setServiceClass(BillingProcessWebServices.class);
		return cxfEndpoint;
	}

	@Bean
	public LoggingOutInterceptor loggingOutInterceptor() {
		return new LoggingOutInterceptor("target/write");
	}

}
