package co.nz.pizzashack.data.dto

import java.io.Serializable;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includeFields=true)
@EqualsAndHashCode(includes=["identity"])
class IndividualDto implements Serializable{
	String firstName
	String lastName
	String identity
	String email
}
