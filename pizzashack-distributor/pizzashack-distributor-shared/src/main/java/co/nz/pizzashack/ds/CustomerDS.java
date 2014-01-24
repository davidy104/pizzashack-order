package co.nz.pizzashack.ds;

import co.nz.pizzashack.data.dto.CustomerDto;

public interface CustomerDS {

	boolean ifCustomerExisted(String customerEmail) throws Exception;

	CustomerDto getCustomerByEmail(String customerEmail) throws Exception;

	CustomerDto getCustomerById(Long customerId) throws Exception;

	CustomerDto getCustomerByName(String customerName) throws Exception;

	CustomerDto createCustomer(CustomerDto customer) throws Exception;

	void updateCustomer(Long customerId, CustomerDto customer) throws Exception;
}
