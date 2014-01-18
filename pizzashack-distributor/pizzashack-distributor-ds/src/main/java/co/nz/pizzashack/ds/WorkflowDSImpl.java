package co.nz.pizzashack.ds;

import static co.nz.pizzashack.data.predicates.WorkflowPredicates.findByNameAndCategory;
import static co.nz.pizzashack.data.predicates.WorkflowPredicates.findByProcessDefinitionKeyAndCategory;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.DuplicatedException;
import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.converter.WorkflowConverter;
import co.nz.pizzashack.data.dto.WorkflowDto;
import co.nz.pizzashack.data.model.WorkflowModel;
import co.nz.pizzashack.data.repository.WorkflowRepository;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class WorkflowDSImpl implements WorkflowDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WorkflowDSImpl.class);

	@Resource
	private WorkflowConverter workflowConverter;

	@Resource
	private WorkflowRepository workflowRepository;

	@Resource
	private RepositoryService repositoryService;

	@Override
	public Set<WorkflowDto> getAllWorkflows() throws Exception {
		LOGGER.info("getAllWorkflows start:{} ");
		Set<WorkflowDto> resultSet = null;
		List<WorkflowModel> workflowList = workflowRepository.findAll();
		if (workflowList != null && workflowList.size() > 0) {
			resultSet = new HashSet<WorkflowDto>();
			for (WorkflowModel model : workflowList) {
				resultSet.add(workflowConverter.toDto(model));
			}
		}
		LOGGER.info("getAllWorkflows end:{} ");
		return resultSet;
	}

	@Override
	public WorkflowDto getWorkflowDtoByProcessDefinitionKey(
			String processDefinitionKey, String category) throws Exception {
		LOGGER.info("getWorkflowDtoByProcessDefinitionKey start:{}",
				processDefinitionKey);
		LOGGER.info("category:{}", category);
		WorkflowDto found = null;

		WorkflowModel foundModel = workflowRepository
				.findOne(findByProcessDefinitionKeyAndCategory(
						processDefinitionKey, category));

		if (foundModel == null) {
			throw new NotFoundException(
					"workflow not existed by processDefinitionKey["
							+ processDefinitionKey + " and category["
							+ category + "]");
		}

		found = workflowConverter.toDto(foundModel);
		LOGGER.info("getWorkflowDtoByProcessDefinitionKey end:{}", found);
		return found;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public WorkflowDto deployWorkflow(String name, String category,
			String... classpathResources) throws Exception {
		LOGGER.info("deployWorkflow start:{}");
		WorkflowDto deployed = null;
		WorkflowModel model = workflowRepository.findOne(findByNameAndCategory(
				name, category));
		if (model != null) {
			throw new DuplicatedException("Workflow already existed");
		}
		model = WorkflowModel.getBuilder(name, category).build();
		model.setCreateTime(new Date());

		DeploymentBuilder builder = repositoryService.createDeployment()
				.category(category);
		for (String classpathResource : classpathResources) {
			builder = builder.addClasspathResource(classpathResource);
		}

		String deployId = builder.deploy().getId();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().deploymentId(deployId)
				.singleResult();
		LOGGER.info("processDefinition:{}", processDefinition);

		model.setDeployId(deployId);
		model.setProcessDefinitionId(processDefinition.getId());
		model.setProcessDefinitionKey(processDefinition.getKey());
		model = workflowRepository.save(model);
		deployed = workflowConverter.toDto(model);
		LOGGER.info("deployWorkflow end:{}", deployed);
		return deployed;
	}

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public void undeployWorkflow(Long workflowId) throws Exception {
		LOGGER.info("undeployWorkflow start:{}", workflowId);
		WorkflowModel model = workflowRepository.findOne(workflowId);
		if (model == null) {
			throw new NotFoundException("workflow not existed by id["
					+ workflowId + "");
		}
		repositoryService.deleteDeployment(model.getDeployId(), true);
		LOGGER.info("undeployWorkflow end:{}");
	}

	@Override
	public WorkflowDto getWorkflowByNameAndCategory(String name, String category)
			throws Exception {
		LOGGER.info("getWorkflowByNameAndCategory start:{}");
		WorkflowDto found = null;
		WorkflowModel model = workflowRepository.findOne(findByNameAndCategory(
				name, category));
		if (model == null) {
			throw new NotFoundException("workflow not existed by name[" + name
					+ "] and category[" + category + "]");
		}
		found = workflowConverter.toDto(model);
		LOGGER.info("getWorkflowByNameAndCategory end:{}", found);
		return found;
	}

	@Override
	public WorkflowDto getWorkflowById(Long workflowId) throws Exception {
		LOGGER.info("getWorkflowById start:{}");
		WorkflowDto found = null;
		WorkflowModel model = workflowRepository.findOne(workflowId);
		if (model == null) {
			throw new NotFoundException("workflow not existed by id["
					+ workflowId + "");
		}
		found = workflowConverter.toDto(model);
		LOGGER.info("getWorkflowById end:{}", found);
		return found;
	}

}
