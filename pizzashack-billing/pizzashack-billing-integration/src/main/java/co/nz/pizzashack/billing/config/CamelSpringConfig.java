package co.nz.pizzashack.billing.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.jta.JtaTransactionManager;

import co.nz.pizzashack.billing.integration.route.AccountServicesRoute;
import co.nz.pizzashack.billing.integration.route.BillingRoute;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;

@Configuration
@PropertySource("classpath:activitymq-config.properties")
public class CamelSpringConfig {

	@Inject
	private ApplicationContext context;

	@Resource
	private JdbcDataSource xaJdbcDataSource;

	@Resource
	private SqlComponent sqlComponent;

	@Resource
	private Environment environment;

	@Resource
	private CxfEndpoint accountWsEndpoint;

	@Resource
	private BillingRoute billingRoute;

	@Resource
	private AccountServicesRoute accountServicesRoute;

	private static final String ACTIVITYMQ_URL = "activitymq_url";
	// private static final String ACTIVITYMQ_TRANSACTED =
	// "activitymq_transacted";
	// private static final String ACTIVITYMQ_MAXCONNECTIONS =
	// "activitymq_maxConnections";
	private static final String ACTIVITYMQ_SENDTIMEOUT = "activitymq_sendTimeoutInMillis";
	private static final String ACTIVITYMQ_WATCHTOPICADVISORIES = "activitymq_watchTopicAdvisories";

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
	}

	@Bean
	public AtomikosConnectionFactoryBean atomikosConnectionFactoryBean() {
		AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
		atomikosConnectionFactoryBean
				.setXaConnectionFactory(mqXaConnectionFactory());
		atomikosConnectionFactoryBean.setUniqueResourceName("xa.activemq");
		atomikosConnectionFactoryBean.setMaxPoolSize(10);
		atomikosConnectionFactoryBean.setIgnoreSessionTransactedFlag(false);
		return atomikosConnectionFactoryBean;
	}

	@Bean
	public AtomikosDataSourceBean atomikosDataSourceBean() {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setXaDataSource(xaJdbcDataSource);
		atomikosDataSourceBean.setUniqueResourceName("xa.h2");
		return atomikosDataSourceBean;
	}

	@Bean
	@Qualifier("jtaTxManager")
	public JtaTransactionManager jtaTransactionManager() throws Exception {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		userTransactionManager.init();
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(300);

		JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
		jtaTransactionManager.setTransactionManager(userTransactionManager);
		jtaTransactionManager.setUserTransaction(userTransactionImp);
		return jtaTransactionManager;
	}

	@Bean
	public SpringTransactionPolicy propagationRequired() throws Exception {
		SpringTransactionPolicy propagationRequired = new SpringTransactionPolicy();
		propagationRequired.setTransactionManager(jtaTransactionManager());
		propagationRequired.setPropagationBehaviorName("PROPAGATION_REQUIRED");
		return propagationRequired;
	}

	@Bean
	public ActiveMQXAConnectionFactory mqXaConnectionFactory() {
		ActiveMQXAConnectionFactory mqXaConnectionFactory = new ActiveMQXAConnectionFactory(
				environment.getRequiredProperty(ACTIVITYMQ_URL));
		mqXaConnectionFactory.setSendTimeout(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_SENDTIMEOUT)));
		mqXaConnectionFactory.setMaxThreadPoolSize(5);
		mqXaConnectionFactory.setWatchTopicAdvisories(Boolean
				.valueOf(environment
						.getRequiredProperty(ACTIVITYMQ_WATCHTOPICADVISORIES)));
		mqXaConnectionFactory.setUseDedicatedTaskRunner(false);
		return mqXaConnectionFactory;
	}

	@Bean
	public JmsComponent jmsComponent() throws Exception {
		JmsComponent jmsComponent = new JmsComponent();
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		jmsConfiguration.setConnectionFactory(mqXaConnectionFactory());
		jmsConfiguration.setTransactionManager(jtaTransactionManager());
		jmsComponent.setConfiguration(jmsConfiguration);
		return jmsComponent;
	}

	@Bean
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);
		camelContext.getExecutorServiceManager().registerThreadPoolProfile(
				custThreadPoolProfile());

		camelContext.addComponent("jms", jmsComponent());
		camelContext.addComponent("sql", sqlComponent);
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("accountWsEndpoint", accountWsEndpoint);

		registry.put("connectionFactory", mqXaConnectionFactory());
		registry.put("atomikos.connectionFactory",
				atomikosConnectionFactoryBean());
		registry.put("atomikos.dataSource", atomikosDataSourceBean());
		registry.put("jta.transactionManager", jtaTransactionManager());
		registry.put("PROPAGATION_REQUIRED", propagationRequired());

		camelContext.setRegistry(registry);
		camelContext.addRoutes(billingRoute);
		camelContext.addRoutes(accountServicesRoute);
		return camelContext;
	}

	@Bean
	public ThreadPoolProfile custThreadPoolProfile() {
		ThreadPoolProfile profile = new ThreadPoolProfile();
		profile.setId("genericThreadPool");
		profile.setKeepAliveTime(120L);
		profile.setPoolSize(2);
		profile.setMaxPoolSize(10);
		profile.setTimeUnit(TimeUnit.SECONDS);
		profile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
		return profile;
	}
}
