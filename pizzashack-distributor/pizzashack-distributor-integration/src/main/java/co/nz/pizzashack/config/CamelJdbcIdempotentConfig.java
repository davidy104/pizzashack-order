package co.nz.pizzashack.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * JdbcIdempotent is only for order process, here we use JTA transaction
 * 
 * @author david
 */
@Configuration
public class CamelJdbcIdempotentConfig {

	@Resource
	private DataSource dataSource;
	
	@Resource
	private PlatformTransactionManager transactionManager;

	@Bean
	public SqlComponent sqlComponent() {
		SqlComponent sqlComponent = new SqlComponent();
		sqlComponent.setDataSource(dataSource);
		return sqlComponent;
	}

	@Bean
	public JdbcMessageIdRepository orderJdbcMessageIdRepository() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(
				transactionManager);
		JdbcMessageIdRepository jdbcMessageIdRepository = new JdbcMessageIdRepository(
				dataSource, transactionTemplate, "orderMqConsumer");

		jdbcMessageIdRepository
				.setTableExistsString("SELECT 1 FROM T_ORDER_REQUEST_HISTORY WHERE 1 = 0");

		jdbcMessageIdRepository
				.setDeleteString("DELETE FROM T_ORDER_REQUEST_HISTORY WHERE processor_name = ? AND message_id = ?");
		jdbcMessageIdRepository
				.setQueryString("SELECT COUNT(*) FROM T_ORDER_REQUEST_HISTORY WHERE processor_name = ? AND message_id = ?");
		jdbcMessageIdRepository
				.setInsertString("INSERT INTO T_ORDER_REQUEST_HISTORY (processor_name, message_id, created_at) VALUES (?, ?, ?)");

		return jdbcMessageIdRepository;
	}
}
