package co.nz.pizzashack.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.JmsTransactionManager;

import co.nz.pizzashack.integration.route.BillingProcessRoute;
import co.nz.pizzashack.integration.route.OrderProcessRoute;

@Configuration
@PropertySource("classpath:activitymq-config.properties")
public class CamelSpringConfig {

	@Resource
	private JmsComponent jmsComponent;

	@Resource
	private PooledConnectionFactory pooledConnectionFactory;

	@Resource
	private SqlComponent sqlComponent;

	@Resource
	private Environment environment;

	@Inject
	private ApplicationContext context;

	@Resource
	private BillingProcessRoute billingProcessRoute;

	@Resource
	private OrderProcessRoute orderProcessRoute;

	// @Resource
	// private WsBillingProcessRoute wsBillingProcessRoute;

	private static final String ACTIVITYMQ_URL = "activitymq_url";
	private static final String ACTIVITYMQ_TRANSACTED = "activitymq_transacted";
	private static final String ACTIVITYMQ_MAXCONNECTIONS = "activitymq_maxConnections";
	private static final String ACTIVITYMQ_SENDTIMEOUT = "activitymq_sendTimeoutInMillis";
	private static final String ACTIVITYMQ_WATCHTOPICADVISORIES = "activitymq_watchTopicAdvisories";

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				environment.getRequiredProperty(ACTIVITYMQ_URL));
		connectionFactory.setSendTimeout(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_SENDTIMEOUT)));
		connectionFactory.setMaxThreadPoolSize(5);
		connectionFactory.setWatchTopicAdvisories(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_WATCHTOPICADVISORIES)));
		connectionFactory.setUseDedicatedTaskRunner(false);
		return connectionFactory;
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public PooledConnectionFactory pooledConnectionFactory() {
		PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setMaxConnections(Integer.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_MAXCONNECTIONS)));
		pooledConnectionFactory
				.setConnectionFactory(activeMQConnectionFactory());
		return pooledConnectionFactory;
	}

	@Bean
	public JmsComponent jmsComponent() {
		JmsComponent jmsComponent = new JmsComponent();
		JmsConfiguration jmsConfiguration = new JmsConfiguration();
		jmsConfiguration.setConnectionFactory(pooledConnectionFactory());
		jmsConfiguration.setTransactionManager(jmsTransactionManager());
		jmsConfiguration.setTransacted(Boolean.valueOf(environment
				.getRequiredProperty(ACTIVITYMQ_TRANSACTED)));
		jmsConfiguration.setCacheLevelName("CACHE_CONSUMER");
		jmsComponent.setConfiguration(jmsConfiguration);
		return jmsComponent;
	}

	@Bean
	public JmsTransactionManager jmsTransactionManager() {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(pooledConnectionFactory());
		return jmsTransactionManager;
	}

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
	}

	@Bean
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);
		camelContext.getExecutorServiceManager().registerThreadPoolProfile(
				custThreadPoolProfile());
		camelContext.addComponent("jms", jmsComponent());
		camelContext.addComponent("sql", sqlComponent);
		SimpleRegistry registry = new SimpleRegistry();

		camelContext.setRegistry(registry);
		camelContext.addRoutes(billingProcessRoute);
		camelContext.addRoutes(orderProcessRoute);
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
