package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QOrderModel;

import com.mysema.query.types.Predicate;

public class OrderPredicates {
	public static Predicate findByOrderNo(final String orderNo) {
		QOrderModel orderModel = QOrderModel.orderModel;
		return orderModel.orderNo.eq(orderNo);
	}
}
