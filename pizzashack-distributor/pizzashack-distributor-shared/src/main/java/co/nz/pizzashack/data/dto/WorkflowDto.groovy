package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["name","category"])
class WorkflowDto implements Serializable{
	Long wfId
	String name
	String category
	String deployId
	String processDefinitionKey
	String processDefinitionId
	String createTime
	String imgPath
}
