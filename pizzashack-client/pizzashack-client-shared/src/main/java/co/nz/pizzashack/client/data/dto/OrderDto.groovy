package co.nz.pizzashack.client.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["orderNo"])
class OrderDto implements Serializable {
	String orderRequestId
	String orderNo
	Set<OrderDetailsDto> orderDetailsSet
	Integer qty
	BigDecimal totalPrice
	@Delegate
	CustomerDto customer = new CustomerDto();
	String status
	String address
	String orderTime
	String deliverTime

	void addOrderDetails(OrderDetailsDto orderDetails){
		if(!orderDetailsSet){
			orderDetailsSet = new HashSet<OrderDetailsDto>()
		}
		orderDetailsSet << orderDetails
	}
}
