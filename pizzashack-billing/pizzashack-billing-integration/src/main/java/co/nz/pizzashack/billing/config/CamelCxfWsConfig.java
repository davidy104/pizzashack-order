package co.nz.pizzashack.billing.config;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import co.nz.pizzashack.billing.integration.ws.BillingWebServices;

@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-extension-soap.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml"})
public class CamelCxfWsConfig {

	/**
	 * http://localhost:8112/pizzashackbilling/ws/accountWs?wsdl
	 * @return
	 */
	@Bean
	public CxfEndpoint accountWsEndpoint() {
		CxfEndpoint cxfEndpoint = new CxfEndpoint();
		cxfEndpoint.setAddress("/accountWs");
		cxfEndpoint.setServiceClass(BillingWebServices.class);
		return cxfEndpoint;
	}
}
