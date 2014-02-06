package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustEmail;
import static co.nz.pizzashack.data.predicates.CustomerPredicates.findByCustName;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.DuplicatedException;
import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.CustomerConverter;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.model.CustomerModel;
import co.nz.pizzashack.data.repository.CustomerRepository;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class CustomerDSImpl implements CustomerDS {

	@Resource
	private CustomerRepository customerRepository;

	@Resource
	private CustomerConverter customerConverter;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerDSImpl.class);

	@Override
	public boolean ifCustomerExisted(String customerEmail) throws Exception {
		LOGGER.info("ifCustomerExisted start:{} ", customerEmail);
		CustomerModel customer = customerRepository
				.findOne(findByCustEmail(customerEmail));
		if (customer != null) {
			return true;
		}
		return false;
	}

	@Override
	public CustomerDto getCustomerByEmail(String customerEmail)
			throws Exception {
		LOGGER.info("getCustomerByEmail start:{} ", customerEmail);
		CustomerDto dto = null;
		CustomerModel customerModel = customerRepository
				.findOne(findByCustEmail(customerEmail));
		if (customerModel == null) {
			throw new NotFoundException("Customer not found by email["
					+ customerEmail + "]");
		}
		dto = customerConverter.toDto(customerModel);
		LOGGER.info("getCustomerByEmail end:{} ", dto);
		return dto;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public CustomerDto createCustomer(CustomerDto customer) throws Exception {
		LOGGER.info("createCustomer start:{} ", customer);
		CustomerDto added = null;
		String custEmail = customer.getCustomerEmail();
		if (this.ifCustomerExisted(custEmail)) {
			throw new DuplicatedException(
					"Customer already existed with email[" + custEmail + "]");
		}
		CustomerModel addedModel = customerConverter.toModel(customer);
		addedModel = customerRepository.save(addedModel);
		added = customerConverter.toDto(addedModel);
		LOGGER.info("createCustomer end:{} ");
		return added;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void updateCustomer(Long customerId, CustomerDto customer)
			throws Exception {
		LOGGER.info("updateCustomer start:{} ", customerId);
		LOGGER.info("updated customer:{} ", customer);
		CustomerModel foundModel = customerRepository.findOne(customerId);
		if (foundModel == null) {
			throw new NotFoundException("Customer not found by customerId["
					+ customerId + "]");
		}
		foundModel.setCustomerName(customer.getCustomerName());
		foundModel.setEmail(customer.getCustomerEmail());
		LOGGER.info("updateCustomer end:{} ", foundModel);
	}

	@Override
	public CustomerDto getCustomerById(Long customerId) throws Exception {
		LOGGER.info("getCustomerById start:{} ", customerId);
		CustomerDto found = null;
		CustomerModel foundModel = customerRepository.findOne(customerId);
		if (foundModel == null) {
			throw new NotFoundException("Customer not found by customerId["
					+ customerId + "]");
		}
		found = customerConverter.toDto(foundModel);
		LOGGER.info("getCustomerById end:{} ", found);
		return found;
	}

	@Override
	public CustomerDto getCustomerByName(String customerName) throws Exception {
		LOGGER.info("getCustomerByName start:{} ", customerName);
		CustomerDto dto = null;
		CustomerModel customerModel = customerRepository
				.findOne(findByCustName(customerName));
		if (customerModel == null) {
			throw new NotFoundException("Customer not found by customerName["
					+ customerName + "]");
		}
		dto = customerConverter.toDto(customerModel);
		LOGGER.info("getCustomerByName end:{} ", dto);
		return dto;
	}

}
