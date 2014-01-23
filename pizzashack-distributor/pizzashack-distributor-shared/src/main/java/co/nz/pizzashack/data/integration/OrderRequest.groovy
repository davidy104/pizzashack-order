package co.nz.pizzashack.data.integration

import groovy.transform.ToString

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

import co.nz.pizzashack.data.dto.CustomerDto
import co.nz.pizzashack.utils.BigDecimalAdapter

@ToString(includeNames = true, includeFields=true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class OrderRequest implements Serializable {

	Set<OrderDetails> orderDetailsSet

	@XmlElement
	Integer qty

	@XmlElement
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	BigDecimal totalPrice

	CustomerDto customer

	@XmlElement
	String address

	@XmlElement
	String orderTime

	void addOrderDetails(OrderDetails orderDetails){
		if(!orderDetailsSet){
			orderDetailsSet = new HashSet<OrderDetails>()
		}
		orderDetailsSet << orderDetails
	}
}
