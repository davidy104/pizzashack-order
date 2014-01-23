package co.nz.pizzashack.client.data.dto;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["customerEmail"])
class CustomerDto implements Serializable{
	Long custId
	String customerName
	String customerEmail
	String level
}
