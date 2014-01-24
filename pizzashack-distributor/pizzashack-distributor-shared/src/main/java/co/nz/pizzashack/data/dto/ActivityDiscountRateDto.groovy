package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["pizzaName"])
class ActivityDiscountRateDto implements Serializable{

	Long rateId
	String activityCode
	String description
	String pizzaName
	String effectTime
	String expireTime
	BigDecimal rate
}
