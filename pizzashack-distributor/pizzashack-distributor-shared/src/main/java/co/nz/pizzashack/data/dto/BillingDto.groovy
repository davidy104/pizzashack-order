package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.pizzashack.utils.BigDecimalAdapter

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["billingRequestId"])
@XmlRootElement(name="billingRequest")
@XmlAccessorType(XmlAccessType.FIELD)
class BillingDto implements Serializable{
	@XmlElement
	String billingRequestId
	@XmlElement
	String orderNo

	String billingCode
	String billingMessage
	String billingTime

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal billingAmount

	@Delegate
	AccountDto account = new AccountDto()
}
