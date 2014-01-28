package co.nz.pizzashack.data.dto

import java.io.Serializable;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName"])
class OrderDetailsDto implements Serializable{
	Long orderDetailId
	String pizzaName
	Integer qty = 0
	BigDecimal totalPrice = BigDecimal.ZERO
}
