package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QDepartmentModel;

import com.mysema.query.types.Predicate;

public class DepartmentPredicates {
	public static Predicate findByDeptname(final String deptname) {
		QDepartmentModel departmentModel = QDepartmentModel.departmentModel;
		return departmentModel.deptName.eq(deptname);
	}
}
