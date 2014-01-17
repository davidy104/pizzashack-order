package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["orderNo","orderId"])
class OrderDto implements Serializable {
	Long orderId
	String orderNo
	Set<OrderPizzaDto> pizzaOrders
	Integer qty
	String totalPrice

	@Delegate
	CustomerDto customer = new CustomerDto()
	String address
	String status
	String orderTime
	String deliverTime

	void addPizzaOrder(OrderPizzaDto pizzaOrder){
		if(!pizzaOrders){
			pizzaOrders = new HashSet<OrderPizzaDto>()
		}
		pizzaOrders << pizzaOrder
	}
}
