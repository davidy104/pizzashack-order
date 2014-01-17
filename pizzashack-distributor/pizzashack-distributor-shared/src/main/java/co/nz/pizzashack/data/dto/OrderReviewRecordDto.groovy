package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["reviewRecordId"])
class OrderReviewRecordDto implements Serializable{
	Long reviewRecordId
	String orderNo
	String content
	String reviewResult
	String createTime
	StaffDto reviewer
}
