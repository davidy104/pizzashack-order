package co.nz.pizzashack.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.config.ApplicationConfiguration;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.ds.CustomerDS;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class CustomerDbTest {

	@Resource
	private CustomerDS customerDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerDbTest.class);

	@Test
	public void testCreateCustomer() throws Exception {
		CustomerDto customer = CustomerTestUtils.createDto();
		customer = customerDs.createCustomer(customer);
		assertNotNull(customer.getCustId());
	}

}
