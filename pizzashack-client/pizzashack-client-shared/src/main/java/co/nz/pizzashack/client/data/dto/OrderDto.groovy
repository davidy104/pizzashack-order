package co.nz.pizzashack.client.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["orderNo"])
class OrderDto implements Serializable {
	Long orderId
	String orderNo
	Set<OrderDetailsDto> orderDetailsSet
	Integer qty = 0
	BigDecimal totalPrice=BigDecimal.ZERO
	@Delegate
	CustomerDto customer = new CustomerDto();
	
	//dataEntry,pendingOnBilling,pendingOnReview,rejected,delivered
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
