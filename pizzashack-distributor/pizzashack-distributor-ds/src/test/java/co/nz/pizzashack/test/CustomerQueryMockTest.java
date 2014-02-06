package co.nz.pizzashack.test;

import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustEmail;
import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustName;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import co.nz.pizzashack.data.converter.CustomerConverter;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.model.CustomerModel;
import co.nz.pizzashack.data.repository.CustomerRepository;
import co.nz.pizzashack.ds.CustomerDSImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerQueryMockTest {

	@Mock
	private CustomerRepository customerRepositoryMock;

	@InjectMocks
	private CustomerDSImpl customerDSImpl;

	@Before
	public void setUp() {
		CustomerConverter customerConverter = new CustomerConverter();
		ReflectionTestUtils.setField(customerDSImpl, "customerConverter",
				customerConverter);
	}

	@Test
	public void testGetCustomerByEmail() throws Exception {
		String custEmail = "david.yuan@yellow.co.nz";
		CustomerModel foundModel = new CustomerModel();
		when(customerRepositoryMock.findOne(findByCustEmail(custEmail)))
				.thenReturn(foundModel);
		CustomerDto actual = customerDSImpl.getCustomerByEmail(custEmail);
		verify(customerRepositoryMock, times(1)).findOne(
				findByCustEmail(custEmail));
		verifyNoMoreInteractions(customerRepositoryMock);
		CustomerTestUtils.assertCustomer(actual, foundModel);
	}

	@Test
	public void testGetCustomerById() throws Exception {
		CustomerModel foundModel = new CustomerModel();
		when(customerRepositoryMock.findOne(1L)).thenReturn(foundModel);
		CustomerDto actual = customerDSImpl.getCustomerById(1L);
		verify(customerRepositoryMock, times(1)).findOne(1L);
		verifyNoMoreInteractions(customerRepositoryMock);
		CustomerTestUtils.assertCustomer(actual, foundModel);
	}

	@Test
	public void testGetCustomerByName() throws Exception {
		String custName = "david";
		CustomerModel foundModel = new CustomerModel();
		when(customerRepositoryMock.findOne(findByCustName(custName)))
				.thenReturn(foundModel);
		CustomerDto actual = customerDSImpl.getCustomerByName(custName);
		verify(customerRepositoryMock, times(1)).findOne(
				findByCustName(custName));
		verifyNoMoreInteractions(customerRepositoryMock);
		CustomerTestUtils.assertCustomer(actual, foundModel);
	}

}
