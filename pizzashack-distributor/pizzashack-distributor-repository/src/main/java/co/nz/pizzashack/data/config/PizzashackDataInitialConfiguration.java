package co.nz.pizzashack.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import co.nz.pizzashack.data.support.PizzashackInitialDataSetup;

@Configuration
public class PizzashackDataInitialConfiguration {

	@Autowired
	private JpaTransactionManager transactionManager;

	@Bean(initMethod = "initialize")
	public PizzashackInitialDataSetup setupData() {
		return new PizzashackInitialDataSetup(new TransactionTemplate(
				this.transactionManager));
	}

}
