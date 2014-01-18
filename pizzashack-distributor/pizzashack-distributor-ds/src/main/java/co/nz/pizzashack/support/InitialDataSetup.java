package co.nz.pizzashack.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.ds.UserDS;

public class InitialDataSetup {

	private UserDS userDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(InitialDataSetup.class);

	public InitialDataSetup(UserDS userDs) {
		this.userDs = userDs;
	}

	public void initialize() throws Exception {
		LOGGER.info("initialize start:{}");
		// init user
		UserDto davidUser = userDs.createUser("david", "123456");
		UserDto bradUser = userDs.createUser("brad", "123456");
		UserDto johnUser = userDs.createUser("john", "123456");
		// create admin user, just for operation
		userDs.createUser("admin", "123456");
	}
}
