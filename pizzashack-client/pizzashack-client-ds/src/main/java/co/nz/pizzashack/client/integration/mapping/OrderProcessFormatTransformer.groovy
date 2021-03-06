package co.nz.pizzashack.client.integration.mapping

import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder

import org.springframework.stereotype.Component

import co.nz.pizzashack.client.data.dto.CustomerDto
import co.nz.pizzashack.client.data.dto.OrderDetailsDto
import co.nz.pizzashack.client.data.dto.OrderDto

@Component
@Slf4j
class OrderProcessFormatTransformer {

	/**
	 *  <order>
	 * 	<address></address>
	 * 	<order-list>
	 * 		<order-details>
	 * 			<pizza-name></pizza-name>
	 * 			<quantity></quantity>
	 * 		</order-details>
	 * 		<order-details>
	 * 			<pizza-name></pizza-name>
	 * 			<quantity></quantity>
	 * 		</order-details>
	 * </order-list>
	 * 	
	 * 	<customer>
	 * 		<name></name>
	 * 		<email></email>
	 * 	</customer>
	 * </order>
	 * @param orderDto
	 * @return
	 */
	String orderRepXmlMarshal(OrderDto orderDto){
		log.info "orderRepXmlMarshal start:{} $orderDto"
		Set<OrderDetailsDto> orderDetailsSet = orderDto.orderDetailsSet
		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)

		builder.order() {
			'address' "${orderDto.address}"

			if(orderDetailsSet && orderDetailsSet.size() > 0){
				"order-list"(){
					orderDetailsSet.each {OrderDetailsDto orderDetails->
						"order-details"(){
							'pizza-name' "${orderDetails.pizzaName}"
							'quantity' "${orderDetails.qty}"
						}
					}
				}
			}

			customer(){
				'name' "${orderDto.customer.customerName}"
				'email' "${orderDto.customer.customerEmail}"
			}
		}

		xmlStr = sw.toString()
		log.info "orderRepXmlMarshal end:{} $xmlStr"
		return xmlStr
	}

	/**
	 * <order-process>
	 * 	<process-id></process-id>
	 * 	<create-time></create-time>
	 * 	<complete-time></complete-time>
	 * 	<operator></operator>
	 * 
	 *  <order>
	 *  	<order-no></order-no>
	 * 		<address></address>
	 * 		<quantity></quantity>
	 * 		<total-price></total-price>
	 * 		<status></status>
	 * 		<order-time></order-time>
	 * 		<deliver-time></deliver-time>
	 * 		<order-list>
	 * 			<order-details>
	 * 				<pizza-name></pizza-name>
	 * 				<quantity></quantity>
	 * 				<total-price></total-price>
	 * 			</order-details>
	 * 			<order-details>
	 * 				<pizza-name></pizza-name>
	 * 				<quantity></quantity>
	 * 				<total-price></total-price>
	 * 			</order-details>
	 * 		</order-list>
	 * 		<customer>
	 * 			<name></name>
	 * 			<email></email>
	 * 		</customer>
	 * 	</order>
	 * 
	 * 	<pending-activity>
	 * 		<name></name>
	 * 		<type></type>
	 * 		<assignee></assignee>
	 * 	</pending-activity>
	 * </order-process>
	 * @return
	 */
	OrderDto orderRespXmlUnmarshal(String xmlStr){
		log.info "orderRespXmlUnmarshal start:{} $xmlStr"
		def orderProcessEle = new XmlSlurper().parseText(xmlStr)
		def orderEle = orderProcessEle.order

		String orderNo = orderEle.'order-no'.text()
		String address = orderEle.address.text()
		log.info "address:{} $address"
		String status = orderEle.status.text()
		String quantity = orderEle.quantity.text()
		log.info "quantity:{} $quantity"
		String orderTime = orderEle.'order-time'.text()
		String deliverTime = orderEle.'deliver-time'.text()
		String totalPriceStr = orderEle.'total-price'.text()
		log.info "totalPrice:{} $totalPriceStr"

		OrderDto orderDto = new OrderDto(orderNo:orderNo,address:address,status:status,orderTime:orderTime,deliverTime:deliverTime)

		if(totalPriceStr){
			orderDto.totalPrice = new BigDecimal(totalPriceStr)
		}
		if(quantity){
			orderDto.qty = Integer.valueOf(quantity)
		}



		def orderListEle = orderEle."order-list"
		if(orderListEle && orderListEle."order-details".size()>0){
			orderListEle."order-details".each {
				String tPrice = it.'total-price'.text()
				String detailQty=it."quantity".text()
				OrderDetailsDto orderDetailsDto = new OrderDetailsDto(pizzaName:it."pizza-name".text(),qty:Integer.valueOf(detailQty),
				totalPrice:new BigDecimal(tPrice))
				orderDto.addOrderDetails(orderDetailsDto)
			}
		}

		def custEle = orderEle.customer
		CustomerDto customer = new CustomerDto(customerName:custEle.name.text(),customerEmail:custEle.email.text())
		orderDto.customer = customer
		log.info "orderRespXmlUnmarshal end:{} $orderDto"
		return orderDto
	}
}
