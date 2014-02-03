package co.nz.pizzashack.client.data.dto

import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
class BillingDto implements Serializable{
	String orderNo
	BigDecimal billingAmount

	@Delegate
	AccountDto account = new AccountDto()
}
