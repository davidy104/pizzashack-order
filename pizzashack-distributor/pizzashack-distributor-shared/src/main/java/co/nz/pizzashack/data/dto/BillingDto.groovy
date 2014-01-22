package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["billingRequestId"])
class BillingDto {
	String billingRequestId
	String orderNo
	String billingCode
	String billingMessage
	String billingTime
	BigDecimal billingAmount

	@Delegate
	AccountDto account = new AccountDto()
}
