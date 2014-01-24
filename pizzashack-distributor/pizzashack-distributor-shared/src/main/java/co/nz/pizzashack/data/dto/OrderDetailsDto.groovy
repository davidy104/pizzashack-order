package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName"])
class OrderDetailsDto {
	Long orderDetailId
	String pizzaName
	Integer qty
	BigDecimal totalPrice
}
