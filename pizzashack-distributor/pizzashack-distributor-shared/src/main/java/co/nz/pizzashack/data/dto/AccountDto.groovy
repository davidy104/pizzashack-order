package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["accountNo","securityNo"])
@XmlRootElement(name="account")
@XmlAccessorType(XmlAccessType.FIELD)
class AccountDto implements Serializable{

	//credit,cash,debit
	@XmlElement
	String paymode = 'credit'
	@XmlElement
	String accountNo
	@XmlElement
	String expireDate
	@XmlElement
	String securityNo


}
