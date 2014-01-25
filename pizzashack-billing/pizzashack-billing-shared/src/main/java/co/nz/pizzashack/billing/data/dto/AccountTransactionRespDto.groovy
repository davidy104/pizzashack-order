package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["transactionNo"])
@XmlRootElement(name="account-transaction")
@XmlAccessorType(XmlAccessType.FIELD)
class AccountTransactionRespDto implements Serializable{
	Long accountId
	@XmlElement(name="transaction-no")
	String transactionNo
	@XmlElement(name="account-no")
	String accountNo
	//000: accept;
	//001:account not found;
	//002:balance is zero; 003:expired; 004: not enough balance
	@XmlElement
	String code
	@XmlElement
	String reasons
	@XmlElement(name="create-time")
	String createTime
}
