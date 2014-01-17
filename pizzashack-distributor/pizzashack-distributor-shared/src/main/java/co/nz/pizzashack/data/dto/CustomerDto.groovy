package co.nz.pizzashack.data.dto;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["customerEmail","custId"])
class CustomerDto implements Serializable{
	Long custId
	String customerName
	String customerEmail
	String level
}
