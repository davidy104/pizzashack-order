package co.nz.pizzashack.data.predicates;

import co.nz.pizzashack.data.model.QWorkflowModel;

import com.mysema.query.types.Predicate;

public class WorkflowPredicates {
	public static Predicate findByNameAndCategory(final String name,
			final String category) {
		QWorkflowModel workflow = QWorkflowModel.workflowModel;
		return workflow.name.eq(name).and(workflow.category.eq(category));
	}

	public static Predicate findByProcessDefinitionKeyAndCategory(
			final String processDefinitionKey, final String category) {
		QWorkflowModel workflow = QWorkflowModel.workflowModel;
		return workflow.processDefinitionKey.eq(processDefinitionKey).and(
				workflow.category.eq(category));
	}

	public static Predicate findByProcessDefinitionId(
			final String processDefinitionId) {
		QWorkflowModel workflow = QWorkflowModel.workflowModel;
		return workflow.processDefinitionId.eq(processDefinitionId);
	}
}
