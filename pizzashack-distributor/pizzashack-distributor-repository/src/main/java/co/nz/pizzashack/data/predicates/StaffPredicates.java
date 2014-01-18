package co.nz.pizzashack.data.predicates;

import org.drools.core.util.StringUtils;

import co.nz.pizzashack.data.dto.IndividualDto;
import co.nz.pizzashack.data.model.QIndividualModel;
import co.nz.pizzashack.data.model.QStaffModel;
import co.nz.pizzashack.data.model.QUserModel;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

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

	public static Predicate findByComplicatedConditions(
			final IndividualDto person, final Integer role, final Integer level) {
		QStaffModel staffModel = QStaffModel.staffModel;
		QIndividualModel individual = staffModel.individual;

		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		String email = person.getEmail();
		String identity = person.getIdentity();

		BooleanExpression mainExpression = null;

		if (!StringUtils.isEmpty(identity)) {
			return individual.identity.eq(identity);
		}

		if (!StringUtils.isEmpty(email)) {
			return individual.email.eq(email);
		}

		if (!StringUtils.isEmpty(firstName)) {
			mainExpression = individual.firstName.eq(firstName);
		}

		if (!StringUtils.isEmpty(lastName)) {
			if (mainExpression != null) {
				mainExpression = individual.lastName.eq(lastName).and(
						mainExpression);
			} else {
				mainExpression = individual.lastName.eq(lastName);
			}
		}

		if (role != null) {
			if (mainExpression != null) {
				mainExpression = staffModel.role.eq(role).and(mainExpression);
			} else {
				mainExpression = staffModel.role.eq(role);
			}
		}

		if (level != null) {
			if (mainExpression != null) {
				mainExpression = staffModel.level.eq(level).and(mainExpression);
			} else {
				mainExpression = staffModel.level.eq(level);
			}
		}

		return mainExpression;
	}
}
