package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["activityId","name","type"])
class ProcessActivityDto implements Serializable {
	String activityId
	String name
	String type
	String assignee
	Set<String> candidateUsers
	Set<String> candidateGroups
	String duration
	Date startTime
	Date endTime
	Map<String, ProcessActivityDto> outgoingActivityMap
	Map<String, ProcessActivityDto> incomeActivityMap

	static Builder getBuilder(String activityId, String name, String type) {
		return new Builder(activityId, name, type)
	}

	static Builder getBuilder(String activityId, String name,
			String type, String assignee, Date startTime, Date endTime) {
		return new Builder(activityId, name, type, assignee, startTime, endTime)
	}

	static class Builder {
		ProcessActivityDto built
		Builder(String activityId, String name, String type) {
			built = new ProcessActivityDto()
			built.activityId = activityId
			built.name = name
			built.type = type
		}

		Builder(String activityId, String name, String type,
		String assignee, Date startTime, Date endTime) {
			built = new ProcessActivityDto()
			built.activityId = activityId
			built.name = name
			built.type = type
			built.assignee = assignee
			built.startTime = startTime
			built.endTime = endTime
		}

		ProcessActivityDto build() {
			return built
		}
	}


	void addCandidateUser(String candidateUser){
		if(!candidateUsers){
			candidateUsers = new HashSet<String>()
		}
		candidateUsers << candidateUser
	}

	void addcandidateGroup(String candidateGroup){
		if(!candidateGroups){
			candidateGroups = new HashSet<String>()
		}
		candidateGroups << candidateGroup
	}

	void addOutgoingActivity(String transition, ProcessActivityDto outgoingActivity){
		if(!outgoingActivityMap){
			outgoingActivityMap = [:]
		}
		outgoingActivityMap.put(transition,outgoingActivity)
	}

	void addIncomingActivity(String transition, ProcessActivityDto incomingActivity){
		if(!incomeActivityMap){
			incomeActivityMap = [:]
		}
		incomeActivityMap.put(transition,incomingActivity)
	}
}
