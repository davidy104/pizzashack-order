package co.nz.pizzashack.billing.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.jdbcx.JdbcDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@MapperScan("co.nz.pizzashack.billing.data.mapper")
public class InfrastructureContextConfiguration {

	@Resource
	private Environment environment;
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "jdbc.driverClassName";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "jdbc.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "jdbc.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "jdbc.username";

	private static final String PROPERTY_NAME_MAX_POOL_SIZE = "c3p0.maxPoolSize";
	private static final String PROPERTY_NAME_MIN_POOL_SIZE = "c3p0.minPoolSize";
	private static final String PROPERTY_NAME_MAX_IDLE_TIME = "c3p0.maxIdleTime";
	private static final String PROPERTY_NAME_INITIAL_POOL_SIZE = "c3p0.initialPoolSize";
	private static final String PROPERTY_NAME_AUTO_COMMITONCLOSE = "c3p0.autoCommitOnClose";

	private static final String TYPE_ALIASES_PACKAGE = "mybatis.typeAliasesPackage";

	@Value("classpath:scheam.sql")
	private org.springframework.core.io.Resource H2_SCHEMA_SCRIPT;

	@Value("classpath:test-data.sql")
	private org.springframework.core.io.Resource H2_DATA_SCRIPT;

	@Value("classpath:drop-data.sql")
	private org.springframework.core.io.Resource H2_CLEANER_SCRIPT;

	@Value("classpath:co/nz/pizzashack/billing/data/mapper/sqlmap/*-mapper.xml")
	private org.springframework.core.io.Resource[] MAPPER_XMLS;

	@Bean
	public DataSource dataSource() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setJdbcUrl(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUser(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		dataSource.setMaxPoolSize(Integer.valueOf(environment
				.getRequiredProperty(PROPERTY_NAME_MAX_POOL_SIZE)));
		dataSource.setMinPoolSize(Integer.valueOf(environment
				.getRequiredProperty(PROPERTY_NAME_MIN_POOL_SIZE)));
		dataSource.setMaxIdleTime(Integer.valueOf(environment
				.getRequiredProperty(PROPERTY_NAME_MAX_IDLE_TIME)));
		dataSource.setInitialPoolSize(Integer.valueOf(environment
				.getRequiredProperty(PROPERTY_NAME_INITIAL_POOL_SIZE)));
		dataSource.setAutoCommitOnClose(Boolean.valueOf(environment
				.getRequiredProperty(PROPERTY_NAME_AUTO_COMMITONCLOSE)));
		return dataSource;
	}

	@Bean
	public JdbcDataSource xaJdbcDataSource() {
		JdbcDataSource xaJdbcDataSource = new JdbcDataSource();
		xaJdbcDataSource.setURL(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		xaJdbcDataSource.setUser(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		xaJdbcDataSource.setPassword(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return xaJdbcDataSource;
	}

	@Autowired
	@Bean
	public DataSourceInitializer dataSourceInitializer() throws Exception {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource());
		initializer.setDatabasePopulator(databasePopulator());
		initializer.setDatabaseCleaner(databaseCleaner());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(H2_SCHEMA_SCRIPT);
		populator.addScript(H2_DATA_SCRIPT);
		return populator;
	}

	private DatabasePopulator databaseCleaner() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(H2_CLEANER_SCRIPT);
		return populator;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setTypeAliasesPackage(environment
				.getRequiredProperty(TYPE_ALIASES_PACKAGE));
		sqlSessionFactoryBean.setMapperLocations(MAPPER_XMLS);
		return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
	}

	@Bean
	@Qualifier("localTxManager")
	public PlatformTransactionManager transactionManager() throws Exception {
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(
				dataSource());
		return transactionManager;
	}
}
