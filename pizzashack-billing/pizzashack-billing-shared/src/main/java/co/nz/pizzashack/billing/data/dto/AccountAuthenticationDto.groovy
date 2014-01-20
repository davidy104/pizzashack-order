package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo"])
class AccountAuthenticationDto implements Serializable{
	Long accountId
	String accountNo
	//000: accept; 001:account not found;
	//002:balance is zero; 003:expired; 004: not enough balance
	String code
	String reasons
}
