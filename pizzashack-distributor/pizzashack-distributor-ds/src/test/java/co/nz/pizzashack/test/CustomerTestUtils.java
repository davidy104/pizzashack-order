package co.nz.pizzashack.test;

import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.model.CustomerModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerTestUtils {

	private static final String CUSTOMER_EMAIL = "david.yuan@yellow.co.nz";
	private static final String CUSTOMER_NAME = "david";

	public static void assertCustomer(CustomerDto dto, CustomerModel model) {
		assertEquals(dto.getCustId(), model.getCustId());
		assertEquals(dto.getCustomerEmail(), model.getCustomerName());
		assertEquals(dto.getCustomerName(), model.getCustomerName());
	}

	public static CustomerDto createDto() {
		return createDto(null);
	}

	public static CustomerDto createDto(Long id) {
		CustomerDto dto = new CustomerDto();

		dto.setCustId(id);
		dto.setCustomerEmail(CUSTOMER_EMAIL);
		dto.setCustomerName(CUSTOMER_NAME);
		return dto;
	}
}
