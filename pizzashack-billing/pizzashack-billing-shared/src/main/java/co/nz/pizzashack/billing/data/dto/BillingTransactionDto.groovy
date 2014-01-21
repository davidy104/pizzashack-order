package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountTransNo"])
class BillingTransactionDto {
	@Delegate
	AccountDto account= new AccountDto()

	String accountTransNo
	String transType
	BigDecimal billingAmount
	String createTime
}
