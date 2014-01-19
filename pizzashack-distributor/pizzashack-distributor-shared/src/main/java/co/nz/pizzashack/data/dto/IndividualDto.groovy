package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import org.hibernate.validator.constraints.NotEmpty

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["identity"])
class IndividualDto implements Serializable{

	@NotEmpty
	String firstName

	@NotEmpty
	String lastName

	@NotEmpty
	String identity

	@NotEmpty
	String email
}
