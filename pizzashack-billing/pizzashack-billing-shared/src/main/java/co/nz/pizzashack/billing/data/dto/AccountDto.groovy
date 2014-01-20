package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo","securityNo"])
class AccountDto implements Serializable{
	Long accountId
	//credit,debit
	String paymode
	String accountNo
	BigDecimal balance
	String expireDate
	String securityNo
}
