package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["orderNo","orderId"])
class OrderDto implements Serializable {
	Long orderId
	String orderNo
	Set<OrderDetailsDto> orderDetailsSet
	Integer qty
	BigDecimal totalPrice

	@Delegate
	CustomerDto customer = new CustomerDto()
	String address

	//dataEntry,pendingOnBilling,pendingOnReview,rejected,delivered
	String status
	String orderTime
	String deliverTime

	void addPizzaOrder(OrderDetailsDto orderDetails){
		if(!orderDetailsSet){
			orderDetailsSet = new HashSet<OrderDetailsDto>()
		}
		orderDetailsSet << orderDetails
	}
}
