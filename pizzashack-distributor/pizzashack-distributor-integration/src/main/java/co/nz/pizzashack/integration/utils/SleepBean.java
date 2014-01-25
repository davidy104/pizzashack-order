package co.nz.pizzashack.integration.utils;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class SleepBean {
	public void sleep(String body, Exchange exchange) throws Exception {
		Thread.sleep(500);
	}
}
