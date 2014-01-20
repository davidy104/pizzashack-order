package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo","accountTransNo"])
class AccountHistoryDto {
	Long accountHistId
	String paymode
	String accountNo
	String accountTransNo
	String transType
	BigDecimal transAmount
	String createTime
}
