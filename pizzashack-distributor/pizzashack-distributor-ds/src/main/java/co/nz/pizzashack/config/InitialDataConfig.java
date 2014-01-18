package co.nz.pizzashack.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.nz.pizzashack.ds.UserDS;
import co.nz.pizzashack.support.InitialDataSetup;

@Configuration
public class InitialDataConfig {

	@Resource
	private UserDS userDs;

	@Bean(initMethod = "initialize")
	public InitialDataSetup setupData() {
		return new InitialDataSetup(userDs);
	}
}
