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
@EqualsAndHashCode(includes=["accountNo","securityNo"])

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class AccountDto implements Serializable{
	Long accountId

	//credit,debit
	@XmlElement
	String paymode

	@XmlElement
	String accountNo

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal balance

	@XmlElement
	String expireDate

	@XmlElement
	String securityNo
}
