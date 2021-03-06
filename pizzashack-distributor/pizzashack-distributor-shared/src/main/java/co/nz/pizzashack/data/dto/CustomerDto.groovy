package co.nz.pizzashack.data.dto;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["customerEmail"])
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class CustomerDto implements Serializable{
	Long custId

	@XmlElement
	String customerName

	@XmlElement
	String customerEmail
	String level
}
