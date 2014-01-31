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
	 *  <order>
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
	 * @return
	 */
	OrderDto orderRespXmlUnmarshal(String xmlStr){
		log.info "orderRespXmlUnmarshal start:{} $xmlStr"
		def orderEle = new XmlSlurper().parseText(xmlStr)

		String address = orderEle.address.text()
		String status = orderEle.status.text()
		String orderTime = orderEle.'order-time'.text()
		String deliverTime = orderEle.'deliver-time'.text()
		String totalPrice = orderEle.'total-price'.text()


		OrderDto orderDto = new OrderDto(address:address,status:status,orderTime:orderTime,deliverTime:deliverTime,
		totalPrice:new BigDecimal(totalPrice))

		def orderListEle = orderEle."order-list"
		if(orderListEle && orderListEle."order-details".size()>0){
			orderListEle."order-details".each {
				String tPrice = it.'total-price'.text()
				OrderDetailsDto orderDetailsDto = new OrderDetailsDto(pizzaName:it."pizza-name".text(),qty:it."quantity".text(),
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
