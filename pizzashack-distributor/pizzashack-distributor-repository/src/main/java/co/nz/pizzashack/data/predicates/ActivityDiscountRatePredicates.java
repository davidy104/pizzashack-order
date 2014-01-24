package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QActivityDiscountRateModel;
import co.nz.pizzashack.data.model.QPizzashackModel;

import com.mysema.query.types.Predicate;

public class ActivityDiscountRatePredicates {
	public static Predicate findByActivityCode(final String activityCode) {
		QActivityDiscountRateModel activityDiscountRateModel = QActivityDiscountRateModel.activityDiscountRateModel;
		return activityDiscountRateModel.activityCode.eq(activityCode);
	}

	public static Predicate findByPizzashackId(final Long pizzashackId) {
		QActivityDiscountRateModel activityDiscountRateModel = QActivityDiscountRateModel.activityDiscountRateModel;
		QPizzashackModel pizzashack = activityDiscountRateModel.pizzashack;
		return pizzashack.pizzashackId.eq(pizzashackId);
	}
}
