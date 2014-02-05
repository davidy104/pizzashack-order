package co.nz.pizzashack.client.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


/**
 * test case:
 * accountNo:111111
 * securityNo:111
 * expireDate:2019-06-24
 * paymode:credit
 * 
 * @author david
 *
 */
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo","securityNo"])
class AccountDto implements Serializable{

	//credit,cash,debit
	String paymode = 'credit'
	String accountNo
	String expireDate
	String securityNo

}
