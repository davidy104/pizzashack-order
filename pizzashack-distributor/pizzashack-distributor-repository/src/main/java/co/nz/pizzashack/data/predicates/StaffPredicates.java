package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QIndividualModel;
import co.nz.pizzashack.data.model.QStaffModel;
import co.nz.pizzashack.data.model.QUserModel;

import com.mysema.query.types.Predicate;

public class StaffPredicates {
	public static Predicate findByStaffIdentity(final String identity) {
		QStaffModel staffModel = QStaffModel.staffModel;
		QIndividualModel individual = staffModel.individual;
		return individual.identity.eq(identity);
	}

	public static Predicate findByStaffName(final String firstName,
			final String lastName) {
		QStaffModel staffModel = QStaffModel.staffModel;
		QIndividualModel individual = staffModel.individual;
		return individual.firstName.eq(firstName).and(
				individual.lastName.eq(lastName));
	}

	public static Predicate findByUserName(final String username) {
		QStaffModel staffModel = QStaffModel.staffModel;
		QUserModel user = staffModel.user;
		if (user != null) {
			return user.username.eq(username);
		}
		return null;
	}
}
