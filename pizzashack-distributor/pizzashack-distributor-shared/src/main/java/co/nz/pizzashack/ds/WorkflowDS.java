package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.WorkflowDto;

public interface WorkflowDS {

	WorkflowDto deployWorkflow(String name, String category,
			String... classpathResources) throws Exception;

	void undeployWorkflow(Long workflowId) throws Exception;

	Set<WorkflowDto> getAllWorkflows() throws Exception;

	WorkflowDto getWorkflowByNameAndCategory(String name, String category)
			throws Exception;

	WorkflowDto getWorkflowById(Long workflowId) throws Exception;

	WorkflowDto getWorkflowDtoByProcessDefinitionKey(
			String processDefinitionKey, String category) throws Exception;
}
