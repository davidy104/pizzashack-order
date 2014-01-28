package co.nz.pizzashack.wf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.ProcessActivityDto;

@Component("activitiFacade")
public class ActivitiFacade {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActivitiFacade.class);

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private ManagementService managementService;

	@Resource
	private IdentityService identityService;

	public static final String CANDIDATE_USERS = "candidateUsers";
	public static final String CANDIDATE_GROUPS = "candidateGroups";

	/**
	 * 
	 * @param bizKey
	 * @param processDefinitionId
	 * @param variableMap
	 * @return
	 */
	public ProcessInstance startProcessInstance(String bizKey,
			String processDefinitionId, Map<String, Object> variableMap) {
		LOGGER.info("startProcessInstance start:{}");
		ProcessInstance processInstance = null;
		if (variableMap != null) {
			processInstance = runtimeService.startProcessInstanceById(
					processDefinitionId, bizKey, variableMap);
		} else {
			processInstance = runtimeService.startProcessInstanceById(
					processDefinitionId, bizKey);
		}

		LOGGER.info("startProcessInstance end:{}");
		return processInstance;
	}

	public void signalProcessInstance(String executionId,
			Map<String, Object> processVariables) {
		runtimeService.signal(executionId, processVariables);
	}

	/**
	 * Check if current process completed.
	 * 
	 * @param bizKey
	 * @param processDefinitionId
	 * @return
	 */
	public boolean ifProcessFinishted(String bizKey, String processDefinitionId) {
		HistoricProcessInstance historicProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey)
				.processDefinitionId(processDefinitionId).singleResult();

		if (historicProcessInstance.getEndTime() != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param processInstanceId
	 * @param variableName
	 * @return
	 */
	public Object getVariableFromCurrentProcess(String processInstanceId,
			String variableName) {
		return runtimeService.getVariable(processInstanceId, variableName);
	}

	/**
	 * Get candidate assignment info, including candidate users and candidate
	 * groups
	 * 
	 * @param taskDefinitionKey
	 * @param processDefinitionId
	 * @return
	 */
	public Map<String, Set<String>> getActiviteTaskCandidateAssignmentInfo(
			String taskDefinitionKey, String processDefinitionId) {
		LOGGER.info("getActiviteTaskCandidateAssignmentInfo start:{}");
		Map<String, Set<String>> resultMap = null;
		Set<String> assignees = null;

		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);

		if (def != null) {
			Set<Expression> candidateUserIdExpressions = def
					.getTaskDefinitions().get(taskDefinitionKey)
					.getCandidateUserIdExpressions();
			if (candidateUserIdExpressions != null
					&& candidateUserIdExpressions.size() > 0) {
				resultMap = new HashMap<String, Set<String>>();
				assignees = new HashSet<String>();

				for (Expression expression : candidateUserIdExpressions) {
					String exText = expression.getExpressionText();
					assignees.add(exText);
				}
				resultMap.put(CANDIDATE_USERS, assignees);
			}

			Set<Expression> candidateGroupIdExpressions = def
					.getTaskDefinitions().get(taskDefinitionKey)
					.getCandidateGroupIdExpressions();
			if (candidateGroupIdExpressions != null
					&& candidateGroupIdExpressions.size() > 0) {
				if (resultMap == null) {
					resultMap = new HashMap<String, Set<String>>();
				}
				if (assignees == null) {
					assignees = new HashSet<String>();
				}

				for (Expression expression : candidateGroupIdExpressions) {
					String exText = expression.getExpressionText();
					assignees.add(exText);
				}
				resultMap.put(CANDIDATE_GROUPS, assignees);
			}

		}
		LOGGER.info("getActiviteTaskCandidateAssignmentInfo end:{}");
		return resultMap;
	}

	/**
	 * Get activeTask by bizKey
	 * 
	 * @param bizKey
	 * @param processDefinitionId
	 * @return
	 */
	public Task getActiviteTask(String bizKey, String processDefinitionId) {
		return taskService.createTaskQuery().processInstanceBusinessKey(bizKey)
				.processDefinitionId(processDefinitionId).singleResult();
	}

	/**
	 * 
	 * @param taskName
	 * @param bizKey
	 * @return
	 */
	public Task getActiveTaskByNameAndBizKey(String taskName, String bizKey) {
		return taskService.createTaskQuery().processInstanceBusinessKey(bizKey)
				.taskName(taskName).singleResult();
	}

	/**
	 * Get last end activity info, sometimes we need to check the end type if we
	 * define multiple ends in flow
	 * 
	 * @param processInstanceId
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessActivityDto getLastActivity(String processInstanceId,
			String processDefinitionId) {
		HistoricActivityInstance LastEndhistoricActivityInstance = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.processDefinitionId(processDefinitionId)
				.orderByHistoricActivityInstanceEndTime().desc().list().get(0);

		if (LastEndhistoricActivityInstance != null) {
			ProcessActivityDto processActivityDto = ProcessActivityDto
					.getBuilder(
							LastEndhistoricActivityInstance.getActivityId(),
							LastEndhistoricActivityInstance.getActivityName(),
							LastEndhistoricActivityInstance.getActivityType(),
							LastEndhistoricActivityInstance.getAssignee(),
							LastEndhistoricActivityInstance.getStartTime(),
							LastEndhistoricActivityInstance.getEndTime())
					.build();
			return processActivityDto;
		}
		return null;
	}

	/**
	 * Get all historic activity for given process,
	 * 
	 * @param processInstanceId
	 * @param processDefinitionId
	 * @return activityDto see{@ProcessActivityDto}
	 */
	public Set<ProcessActivityDto> getHistoricActivity(
			String processInstanceId, String processDefinitionId) {
		Set<ProcessActivityDto> results = null;
		List<HistoricActivityInstance> historicActivityInstances = historyService
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.processDefinitionId(processDefinitionId)
				.orderByHistoricActivityInstanceEndTime().desc().list();
		ProcessActivityDto processActivityDto = null;
		if (historicActivityInstances != null) {
			results = new HashSet<ProcessActivityDto>();
			for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
				processActivityDto = ProcessActivityDto.getBuilder(
						historicActivityInstance.getActivityId(),
						historicActivityInstance.getActivityName(),
						historicActivityInstance.getActivityType(),
						historicActivityInstance.getAssignee(),
						historicActivityInstance.getStartTime(),
						historicActivityInstance.getEndTime()).build();

				if (historicActivityInstance.getDurationInMillis() != null) {
					// processActivityDto.setDuration(GeneralUtils
					// .formatLongToTimeStr(historicActivityInstance
					// .getDurationInMillis()));
				}
				LOGGER.info("historic processActivityDto:{} ",
						processActivityDto);
				results.add(processActivityDto);
			}
		}
		return results;
	}

	/**
	 * Get Activity info by executionId, mostly, executionId is same as
	 * processInstanceId. but if there is subprocess, the subprocess has its own
	 * executionId and it is different from mainprocess's processInstanceId
	 * 
	 * @param processDefinitionId
	 * @param executionId
	 * @return
	 */
	public ActivityImpl getExecutionActivity(String processDefinitionId,
			String bizKey, String executionId) {
		LOGGER.info("checkExecutionActivity start:{}");
		LOGGER.info("executionId: " + executionId + ", processDefinitionId: "
				+ processDefinitionId);
		String activitiId = null;

		ExecutionEntity executionEntity = (ExecutionEntity) runtimeService
				.createExecutionQuery().processInstanceBusinessKey(bizKey)
				.executionId(executionId).singleResult();

		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);

		List<ActivityImpl> activityList = processDefinitionEntity
				.getActivities();

		if (executionEntity != null) {
			activitiId = executionEntity.getActivityId();
			if (activityList != null) {
				for (ActivityImpl activityImpl : activityList) {
					String id = activityImpl.getId();
					if (id.equals(activitiId)) {
						return activityImpl;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get basic info of activity by given executionId
	 * 
	 * @param processDefinitionId
	 * @param executionId
	 * @return activityDto see{@ProcessActivityDto}
	 */
	public ProcessActivityDto getExecutionActivityBasicInfo(String bizKey,
			String processDefinitionId, String executionId,
			boolean buildIncomingActivities, boolean buildOutgoingActivities) {
		ProcessActivityDto processActivityDto = null;
		ActivityImpl activity = this.getExecutionActivity(processDefinitionId,
				bizKey, executionId);
		if (activity != null) {
			String activityType = (String) activity.getProperty("type");
			String activityName = (String) activity.getProperty("name");
			processActivityDto = ProcessActivityDto.getBuilder(
					activity.getId(), activityName, activityType).build();

			if (buildIncomingActivities) {
				List<PvmTransition> incomingTransitions = activity
						.getIncomingTransitions();
				for (PvmTransition pvmTransition : incomingTransitions) {
					String transition = pvmTransition.getId();
					PvmActivity incomingActivity = pvmTransition
							.getDestination();
					String incomingActivityType = (String) incomingActivity
							.getProperty("type");
					String incomingActivityName = (String) incomingActivity
							.getProperty("name");
					ProcessActivityDto incomingActivityDto = ProcessActivityDto
							.getBuilder(incomingActivity.getId(),
									incomingActivityName, incomingActivityType)
							.build();
					processActivityDto.addIncomingActivity(transition,
							incomingActivityDto);
				}
			}
			if (buildOutgoingActivities) {
				List<PvmTransition> outgoingTransitions = activity
						.getOutgoingTransitions();
				for (PvmTransition pvmTransition : outgoingTransitions) {
					String transition = pvmTransition.getId();
					PvmActivity outgoingActivity = pvmTransition
							.getDestination();
					String outgoingActivityType = (String) outgoingActivity
							.getProperty("type");
					String outgoingActivityName = (String) outgoingActivity
							.getProperty("name");
					ProcessActivityDto outgoingActivityDto = ProcessActivityDto
							.getBuilder(outgoingActivity.getId(),
									outgoingActivityName, outgoingActivityType)
							.build();
					processActivityDto.addOutgoingActivity(transition,
							outgoingActivityDto);
				}
			}

		}

		return processActivityDto;
	}

	public boolean checkIfUserHasRightForGivenTask(String bizKey,
			String taskName, String username) {

		// Invalid query usage: cannot set both candidateGroup and candidateUser
		if (taskService.createTaskQuery().taskName(taskName)
				.processInstanceBusinessKey(bizKey).taskAssignee(username)
				.count() > 0) {
			return true;
		} else if (taskService.createTaskQuery().taskName(taskName)
				.processInstanceBusinessKey(bizKey).taskCandidateUser(username)
				.count() > 0) {
			return true;
		} else {
			List<Group> groupList = this.identityService.createGroupQuery()
					.groupMember(username).list();
			if (groupList != null) {
				for (Group group : groupList) {
					if (taskService.createTaskQuery().taskName(taskName)
							.processInstanceBusinessKey(bizKey)
							.taskCandidateGroup(group.getId()).count() > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<Task> getAllTasksForUser(String userId) {
		LOGGER.info("getAllTasksForUser start:{}", userId);
		List<Task> totalTaskList = taskService.createTaskQuery()
				.taskAssignee(userId).list();

		List<Task> candidateTaskList = taskService.createTaskQuery()
				.taskCandidateUser(userId).list();
		if (candidateTaskList != null) {
			if (totalTaskList == null) {
				totalTaskList = new ArrayList<Task>();
			}
			totalTaskList.addAll(candidateTaskList);
		}

		Set<String> groupIds = this.getGroupIdsByUserId(userId);
		if (groupIds != null) {
			for (String groupId : groupIds) {
				List<Task> tempGroupTaskList = taskService.createTaskQuery()
						.taskCandidateGroup(groupId).list();
				if (tempGroupTaskList != null) {
					if (totalTaskList == null) {
						totalTaskList = new ArrayList<Task>();
					}
					totalTaskList.addAll(tempGroupTaskList);
				}
			}
		}
		return totalTaskList;
	}

	public Set<String> getGroupIdsByUserId(String userId) {
		List<Group> groupList = identityService.createGroupQuery()
				.groupMember(userId).list();
		Set<String> groupIds = null;

		if (groupList != null && groupList.size() > 0) {
			groupIds = new HashSet<String>();
			for (Group group : groupList) {
				groupIds.add(group.getId());
			}
		}
		return groupIds;
	}

}
