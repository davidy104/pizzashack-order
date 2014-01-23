package co.nz.pizzashack.client.ds;

import co.nz.pizzashack.client.NotFoundException;
import co.nz.pizzashack.client.data.dto.CustomerDto;

public interface CustomerDS {

	CustomerDto getCustomerByEmail(String email) throws NotFoundException;

	CustomerDto createCustomer(CustomerDto customer) throws Exception;

}
