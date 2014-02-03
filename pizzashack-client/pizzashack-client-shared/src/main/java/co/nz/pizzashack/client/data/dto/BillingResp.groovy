package co.nz.pizzashack.client.data.dto

import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
class BillingResp implements Serializable{
	String orderNo
	String billingCode
	String billingMessage
}
