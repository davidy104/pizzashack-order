package co.nz.pizzashack.data.dto

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName","pizzashackId"])
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PizzashackDto implements Serializable{
	@XmlElement
	Long pizzashackId
	@XmlElement
	String pizzaName
	@XmlElement
	String description
	@XmlElement
	BigDecimal price
	@XmlElement
	BigDecimal afterDiscount
	String icon
}
