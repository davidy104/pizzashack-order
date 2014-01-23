package co.nz.pizzashack.data.integration

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.pizzashack.utils.BigDecimalAdapter


@ToString(includeNames = true, includeFields=true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(includes=["orderNo"])
class OrderResponse implements Serializable{
	@XmlElement
	String orderNo

	@XmlElement
	Integer qty

	Set<OrderDetails> orderDetailsSet

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal totalPrice

	@XmlElement
	String status

	@XmlElement
	String orderTime

	@XmlElement
	String deliverTime

	@XmlElement
	String operator

	void addOrderDetails(OrderDetails orderDetails){
		if(!orderDetailsSet){
			orderDetailsSet = new HashSet<OrderDetails>()
		}
		orderDetailsSet << orderDetails
	}
}
