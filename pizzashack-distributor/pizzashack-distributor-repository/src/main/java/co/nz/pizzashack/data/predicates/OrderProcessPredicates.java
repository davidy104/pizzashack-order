package co.nz.pizzashack.data.predicates;

import java.util.Set;

import co.nz.pizzashack.data.model.QOrderModel;
import co.nz.pizzashack.data.model.QOrderProcessModel;

import com.mysema.query.types.Predicate;

public class OrderProcessPredicates {
	public static Predicate findByOrderNo(final String orderNo) {
		QOrderProcessModel orderProcessModel = QOrderProcessModel.orderProcessModel;
		QOrderModel order = orderProcessModel.order;
		return order.orderNo.eq(orderNo);
	}

	public static Predicate findByExecutionIds(final Set<String> executionIds) {
		QOrderProcessModel orderProcessModel = QOrderProcessModel.orderProcessModel;
		return orderProcessModel.executionId.in(executionIds);
	}

	public static Predicate findByCustomerEmail(final String custEmail) {
		QOrderProcessModel orderProcessModel = QOrderProcessModel.orderProcessModel;
		QOrderModel order = orderProcessModel.order;
		return order.customer.email.eq(custEmail);
	}
}
