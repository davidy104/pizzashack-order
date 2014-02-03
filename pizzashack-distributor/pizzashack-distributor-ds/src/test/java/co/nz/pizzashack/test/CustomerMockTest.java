package co.nz.pizzashack.test;

import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustEmail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.nz.pizzashack.data.converter.CustomerConverter;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.model.CustomerModel;
import co.nz.pizzashack.data.repository.CustomerRepository;
import co.nz.pizzashack.ds.CustomerDSImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerMockTest {
	@Mock
	private CustomerRepository customerRepositoryMock;

	@Mock
	private CustomerConverter customerConverterMock;

	@InjectMocks
	private CustomerDSImpl customerDSImpl;

	@Test
	public void testGetCustomerByEmail() throws Exception {
		String custEmail = "david.yuan@yellow.co.nz";
		CustomerModel foundModel = new CustomerModel();
		when(customerRepositoryMock.findOne(findByCustEmail(custEmail)))
				.thenReturn(foundModel);

		CustomerDto found = new CustomerDto();
		when(customerConverterMock.toDto(foundModel)).thenReturn(found);

		CustomerDto actual = customerDSImpl.getCustomerByEmail(custEmail);

		verify(customerRepositoryMock, times(1)).findOne(
				findByCustEmail(custEmail));
		verify(customerConverterMock, times(1)).toDto(foundModel);

		verifyNoMoreInteractions(customerRepositoryMock);
		verifyNoMoreInteractions(customerConverterMock);

		CustomerTestUtils.assertCustomer(actual, foundModel);
	}

	@Test
	public void testCreate() throws Exception {
//		CustomerDto added = CustomerTestUtils.createDto();
//		customerDSImpl.createCustomer(added);
//		
//		ArgumentCaptor<CustomerModel> custArgument = ArgumentCaptor
//				.forClass(CustomerModel.class);
//
//		verify(customerRepositoryMock, times(1)).save(custArgument.capture());
//		verifyNoMoreInteractions(customerRepositoryMock);
//
//		CustomerModel actual = custArgument.getValue();
//		CustomerTestUtils.assertCustomer(added, actual);
	}
}
