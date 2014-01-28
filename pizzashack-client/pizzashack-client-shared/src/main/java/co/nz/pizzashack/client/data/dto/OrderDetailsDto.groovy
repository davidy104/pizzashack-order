package co.nz.pizzashack.client.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName"])
class OrderDetailsDto implements Serializable{
	String pizzaName
	Integer qty = 0
	BigDecimal totalPrice = BigDecimal.ZERO
}
