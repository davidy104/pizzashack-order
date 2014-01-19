package co.nz.pizzashack.data.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["identity"])
class IndividualDto implements Serializable{

	@NotEmpty
	@NotBlank
	String firstName

	@NotEmpty
	String lastName

	@NotEmpty
	String identity

	@NotEmpty
	@Email
	String email
}
