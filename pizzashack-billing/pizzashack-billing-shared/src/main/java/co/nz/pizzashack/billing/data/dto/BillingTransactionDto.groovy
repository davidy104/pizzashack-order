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

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class BillingTransactionDto {

	@XmlElement
	String accountTransNo
	@Delegate
	AccountDto account= new AccountDto()

	@XmlElement
	String transType

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal billingAmount

	@XmlElement
	String createTime
}
