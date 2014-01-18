package co.nz.pizzashack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//@ComponentScan(basePackages = "co.nz.pizzashack")
@ImportResource({ "classpath:activitiAppContext.xml" })
public class ApplicationConfiguration {

}
