package co.nz.pizzashack.data.integration

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.pizzashack.utils.BigDecimalAdapter

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName"])
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class OrderDetails implements Serializable{

	@XmlElement
	String pizzaName

	@XmlElement
	Integer qty

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal totalPrice
}
