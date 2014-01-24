package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QCustomerModel;

import com.mysema.query.types.Predicate;

public class CustomerPredicates {

	public static Predicate findByCustName(final String customerName) {
		QCustomerModel customerModel = QCustomerModel.customerModel;
		return customerModel.customerName.eq(customerName);
	}

	public static Predicate findByCustEmail(final String customerEmail) {
		QCustomerModel customerModel = QCustomerModel.customerModel;
		return customerModel.email.eq(customerEmail);
	}
}
