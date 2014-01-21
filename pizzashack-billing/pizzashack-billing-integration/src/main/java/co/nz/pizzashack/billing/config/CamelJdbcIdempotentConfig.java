package co.nz.pizzashack.billing.config;

import javax.annotation.Resource;

import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * JdbcIdempotent is only for order process, here we use JTA transaction
 *
 * @author david
 */
@Configuration
public class CamelJdbcIdempotentConfig {

	@Resource
	private JdbcDataSource xaJdbcDataSource;

	@Resource
	private JtaTransactionManager jtaTransactionManager;

	@Bean
	public SqlComponent sqlComponent() {
		SqlComponent sqlComponent = new SqlComponent();
		sqlComponent.setDataSource(xaJdbcDataSource);
		return sqlComponent;
	}

	@Bean
	public JdbcMessageIdRepository accountJdbcMessageIdRepository() {
		TransactionTemplate transactionTemplate = new TransactionTemplate(
				jtaTransactionManager);
		JdbcMessageIdRepository jdbcMessageIdRepository = new JdbcMessageIdRepository(
				xaJdbcDataSource, transactionTemplate, "billingMqConsumer");

		jdbcMessageIdRepository
				.setTableExistsString("SELECT 1 FROM T_BILLING_REQUEST_HISTORY WHERE 1 = 0");

		jdbcMessageIdRepository
				.setDeleteString("DELETE FROM T_BILLING_REQUEST_HISTORY WHERE processor_name = ? AND message_id = ?");
		jdbcMessageIdRepository
				.setQueryString("SELECT COUNT(*) FROM T_BILLING_REQUEST_HISTORY WHERE processor_name = ? AND message_id = ?");
		jdbcMessageIdRepository
				.setInsertString("INSERT INTO T_BILLING_REQUEST_HISTORY (processor_name, message_id, created_at) VALUES (?, ?, ?)");

		return jdbcMessageIdRepository;
	}
}
