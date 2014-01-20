package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo"])
class AccountAuthenticationDto implements Serializable{
	String accountNo
	String code
	String reasons
}
