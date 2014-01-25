package co.nz.pizzashack.billing.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.pizzashack.billing.utils.BigDecimalAdapter

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountTransNo"])

@XmlRootElement(name="billing")
@XmlAccessorType(XmlAccessType.FIELD)
class BillingTransactionDto {

	@XmlElement(name="account-transaction-no")
	String accountTransNo
	
	@Delegate
	AccountDto account= new AccountDto()

	@XmlElement(name="transaction-type")
	String transType

	@XmlElement(name="billing-amount")
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal billingAmount

	@XmlElement(name="create-time")
	String createTime
}
