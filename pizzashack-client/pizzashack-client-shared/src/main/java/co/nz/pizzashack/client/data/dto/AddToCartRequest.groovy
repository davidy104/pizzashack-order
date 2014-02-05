package co.nz.pizzashack.client.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzashackId"])
class AddToCartRequest implements Serializable{
	Long pizzashackId
	Integer amount
}
