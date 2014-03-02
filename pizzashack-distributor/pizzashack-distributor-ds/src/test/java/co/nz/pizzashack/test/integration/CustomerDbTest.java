package co.nz.pizzashack.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.config.ApplicationConfiguration;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.ds.CustomerDS;
import co.nz.pizzashack.test.CustomerTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@Ignore
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

	@Test
	public void testUpdateCustomer() throws Exception {
		CustomerDto customer = CustomerTestUtils.createDto();
		customer = customerDs.createCustomer(customer);
		assertNotNull(customer);
		Long custId = customer.getCustId();
		customer.setCustomerName("test01");
		customer.setCustomerEmail("test01@gmail.com");
		customerDs.updateCustomer(custId, customer);

		customer = customerDs.getCustomerById(custId);
		assertNotNull(customer);
		assertEquals("test01@gmail.com", customer.getCustomerEmail());
	}

}
