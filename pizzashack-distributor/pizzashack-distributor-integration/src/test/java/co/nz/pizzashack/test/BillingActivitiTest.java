package co.nz.pizzashack.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.config.ApplicationConfiguration;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.dto.WorkflowDto;
import co.nz.pizzashack.ds.WorkflowDS;
import co.nz.pizzashack.wf.ActivitiFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Ignore("execute when billing app is running")
public class BillingActivitiTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillingActivitiTest.class);

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private ActivitiFacade activitiFacade;

	@Resource
	private WorkflowDS workflowDs;

	private WorkflowDto workflow;

	private String deployId;
	private String definitionId;
	private String definitionKey;

	@Before
	public void initialize() throws Exception {

		workflow = workflowDs.getWorkflowByNameAndCategory(
				"orderBillingProcess", "order");

		// deployId = repositoryService.createDeployment()
		// .addClasspathResource("bpmn/OrderBillingProcess.bpmn20.xml")
		// .deploy().getId();

		deployId = workflow.getDeployId();

		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().deploymentId(deployId)
				.singleResult();
		definitionId = processDefinition.getId();
		definitionKey = processDefinition.getKey();
		LOGGER.info("definitionId:{} ", definitionId);
		LOGGER.info("definitionKey:{} ", definitionKey);
	}

	@Test
	public void testBillingProcessFromFlow() throws Exception {

		BillingDto billingDto = BillingTestUtils.mockBilling();
		// this.transactionalProcess(billingDto);

		String bizKey = billingDto.getOrderNo();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("billingTransBean", billingDto);
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceById(definitionId, bizKey, variables);
		Thread.sleep(3000);

		boolean ifCompleted = activitiFacade.ifProcessFinishted(bizKey,
				definitionId);
		LOGGER.info("completed?:{}", ifCompleted);
		if (!ifCompleted) {
			BillingDto result = (BillingDto) activitiFacade
					.getVariableFromCurrentProcess(processInstance.getId(),
							"billingTransBean");
			LOGGER.info("result:{} ", result);
			ProcessActivityDto pendingActivity = activitiFacade
					.getExecutionActivityBasicInfo(bizKey, definitionId,
							processInstance.getId(), true, true);
			LOGGER.info("pendingActivity:{} ", pendingActivity);
		}
	}

	@Transactional(value = "localTxManager", readOnly = false)
	private void transactionalProcess(BillingDto billingDto) throws Exception {
		String bizKey = billingDto.getOrderNo();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("billingTransBean", billingDto);
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceById(definitionId, bizKey, variables);
		Thread.sleep(3000);
		boolean ifCompleted = activitiFacade.ifProcessFinishted(bizKey,
				definitionId);
		LOGGER.info("completed?:{}", ifCompleted);
		if (!ifCompleted) {
			BillingDto result = (BillingDto) activitiFacade
					.getVariableFromCurrentProcess(processInstance.getId(),
							"billingTransBean");
			LOGGER.info("result:{} ", result);

			ProcessActivityDto pendingActivity = activitiFacade
					.getExecutionActivityBasicInfo(bizKey, definitionId,
							processInstance.getId(), true, true);
			LOGGER.info("pendingActivity:{} ", pendingActivity);
		}
	}

}
