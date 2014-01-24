package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName","pizzashackId"])
class PizzashackDto implements Serializable{
	Long pizzashackId
	String pizzaName
	String description
	BigDecimal price
	BigDecimal afterDiscount
	String icon
}
