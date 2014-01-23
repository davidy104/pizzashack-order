package co.nz.pizzashack.client.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo","securityNo"])
class AccountDto implements Serializable{

	//credit,cash,debit
	String paymode = 'credit'
	String accountNo
	String expireDate
	String securityNo


}
