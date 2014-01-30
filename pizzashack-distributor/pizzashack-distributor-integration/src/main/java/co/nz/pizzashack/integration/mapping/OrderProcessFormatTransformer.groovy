package co.nz.pizzashack.integration.mapping

import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder

import org.springframework.stereotype.Component

import co.nz.pizzashack.data.dto.CustomerDto
import co.nz.pizzashack.data.dto.OrderDetailsDto
import co.nz.pizzashack.data.dto.OrderDto
import co.nz.pizzashack.data.dto.OrderProcessDto
import co.nz.pizzashack.data.dto.ProcessActivityDto

@Component
@Slf4j
class OrderProcessFormatTransformer {

	/**
	 * <order>
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
	 * @param xmlStr
	 * @return
	 */
	OrderDto orderReqXmlUnmarshal(String xmlStr){
		log.info "orderReqXmlUnmarshal start:{} $xmlStr"
		def orderEle = new XmlSlurper().parseText(xmlStr)

		String address = orderEle.address.text()
		OrderDto order = new OrderDto(address:address)


		def orderListEle = orderEle."order-list"
		if(orderListEle && orderListEle."order-details".size()>0){
			orderListEle."order-details".each {
				OrderDetailsDto orderDetailsDto = new OrderDetailsDto(pizzaName:it."pizza-name".text(),qty:it."quantity".text())
				order.addPizzaOrder(orderDetailsDto)
			}
		}

		def custEle = orderEle.customer
		CustomerDto customer = new CustomerDto(customerName:custEle.name.text(),customerEmail:custEle.email.text())
		order.customer = customer
	}

	/**
	 * <order-process>
	 * 	<process-id></process-id>
	 * 	<create-time></create-time>
	 * 	<complete-time></complete-time>
	 * 	<operator></operator>
	 * 
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
	 * 
	 * 	<pending-activity>
	 * 		<name></name>
	 * 		<type></type>
	 * 		<assignee></assignee>
	 * 	</pending-activity>
	 * </order-process>
	 * 
	 * @param orderProcessDto
	 * @return
	 */
	String orderRespXmlMarshal(OrderProcessDto orderProcessDto){
		log.info "orderReqXmlMarshal start:{} $orderProcessDto"

		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)

		OrderDto order = orderProcessDto.order
		ProcessActivityDto pendingActivity = orderProcessDto.pendingActivity
		Set<OrderDetailsDto> orderDetailsSet = order.orderDetailsSet
		CustomerDto customerDto = order.customer

		builder.'order-process'() {
			'process-id' "${orderProcessDto.orderProcessId}"
			'create-time' "${orderProcessDto.createTime}"
			'complete-time' "${orderProcessDto.completeTime}"
			'operator' "${orderProcessDto.operator.username}"

			order(){
				'address' "${order.address}"
				'quantity' "${order.qty}"
				'total-price' "${order.totalPrice}"
				'status' "${order.status}"
				'order-time' "${order.orderTime}"
				'deliver-time' "${order.deliverTime}"

				if(orderDetailsSet && orderDetailsSet.size() > 0){
					"order-list"(){
						orderDetailsSet.each {OrderDetailsDto orderDetails->
							"order-details"(){
								'pizza-name' "${orderDetails.pizzaName}"
								'quantity' "${orderDetails.qty}"
								'total-price' "${orderDetails.totalPrice}"
							}
						}
					}
				}
			}


			customer(){
				'name' "${customerDto.customerName}"
				'email' "${customerDto.customerEmail}"
			}

			if(pendingActivity){
				'pending-activity'(){
					'name' "${pendingActivity.name}"
					'type' "${pendingActivity.type}"
					'assignee' "${pendingActivity.assignee}"
				}
			}
		}

		xmlStr = sw.toString()
		log.info "orderReqXmlMarshal end:{} $xmlStr"
		return xmlStr
	}
}
