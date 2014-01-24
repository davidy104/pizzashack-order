package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QPizzashackModel;

import com.mysema.query.types.Predicate;

public class PizzashackPredicates {
	public static Predicate findByPizzashackName(final String pizzashackName) {
		QPizzashackModel pizzashackModel = QPizzashackModel.pizzashackModel;
		return pizzashackModel.pizzaName.eq(pizzashackName);
	}
}
