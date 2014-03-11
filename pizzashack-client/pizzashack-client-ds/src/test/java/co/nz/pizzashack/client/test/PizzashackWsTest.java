package co.nz.pizzashack.client.test;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.client.config.ApplicationConfiguration;
import co.nz.pizzashack.client.integration.ws.client.pizzashack.stub.PizzashackDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class PizzashackWsTest {
	@Produce
	private ProducerTemplate producer;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackWsTest.class);

	@Test
	public void testGetPizzashackByName() {
		PizzashackDto pizzashackDto = producer.requestBody(
				"cxf:bean:pizzashackEndpoint?dataFormat=POJO",
				"Chicken Parmesan", PizzashackDto.class);

		LOGGER.info("pizzashackDto:{} ", pizzashackDto);
	}

}
