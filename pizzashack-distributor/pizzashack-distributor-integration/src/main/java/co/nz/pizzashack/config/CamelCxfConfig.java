package co.nz.pizzashack.config;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import co.nz.pizzashack.integration.ws.BillingProcessWebServices;
import co.nz.pizzashack.integration.ws.PizzashackWs;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-extension-soap.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml" })
public class CamelCxfConfig {

	// http://localhost:8111/ws/pizzashackWs
	@Bean
	public CxfEndpoint pizzashackEndpoint() {
		CxfEndpoint cxfEndpoint = new CxfEndpoint();
		cxfEndpoint.setAddress("/pizzashackWs");
		cxfEndpoint.setServiceClass(PizzashackWs.class);
		cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
		cxfEndpoint.setMtomEnabled(true);
		return cxfEndpoint;
	}

	// http://localhost:8111/ws/billingWs?wsdl
	@Bean
	public CxfEndpoint billingInboundEndpoint() {
		CxfEndpoint cxfEndpoint = new CxfEndpoint();
		cxfEndpoint.setAddress("/billingWs");
		cxfEndpoint.setServiceClass(BillingProcessWebServices.class);
		LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
		loggingOutInterceptor.setPrettyLogging(true);
		cxfEndpoint.getOutInterceptors().add(loggingOutInterceptor);
		return cxfEndpoint;
	}

	// @Bean
	// public LoggingOutInterceptor loggingOutInterceptor() {
	// return new LoggingOutInterceptor("target/write");
	// }

}
