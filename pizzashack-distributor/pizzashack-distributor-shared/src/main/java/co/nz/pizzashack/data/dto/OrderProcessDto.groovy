package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["orderProcessId"])
class OrderProcessDto implements Serializable{
	Long orderProcessId
	@Delegate
	OrderDto order = new OrderDto()

	String mainProcessInstanceId
	String mainProcessDefinitionId

	String activeProcesssInstanceId
	String activeProcessDefinitionId

	String createTime
	String completeTime
	String executionId
	ProcessActivityDto pendingActivity
	UserDto operator
}
