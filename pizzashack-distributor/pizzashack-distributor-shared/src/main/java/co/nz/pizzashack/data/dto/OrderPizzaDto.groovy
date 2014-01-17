package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName"])
class OrderPizzaDto {
	Long pizzaOrderId
	String pizzaName
	Integer qty
	String totalPrice
}
