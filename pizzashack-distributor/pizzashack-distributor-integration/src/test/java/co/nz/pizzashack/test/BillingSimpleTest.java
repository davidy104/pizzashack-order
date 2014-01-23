package co.nz.pizzashack.test;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.TestApplicationConfiguration;
import co.nz.pizzashack.data.dto.BillingDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfiguration.class)
@Ignore("execute when billing app is running")
public class BillingSimpleTest {

	@Produce
	private ProducerTemplate producer;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingSimpleTest.class);
	@Test
	public void testSimpleBillingRoute() {

		BillingDto billingDto = BillingTestUtils.mockBilling();

		billingDto = producer.requestBodyAndHeader(
				"direct:doBillingIntegration", billingDto, "messageId",
				billingDto.getBillingRequestId(), BillingDto.class);

		LOGGER.info("get result:{} ", billingDto);
	}

}
