package co.nz.pizzashack.billing.test;

import java.math.BigDecimal;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.billing.config.ApplicationConfiguration;
import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto;
import co.nz.pizzashack.billing.data.dto.AccountDto;
import co.nz.pizzashack.billing.data.dto.BillingTransactionDto;
import co.nz.pizzashack.billing.ds.AccountDS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
// @Ignore
public class AccountDSTest {

	@Resource
	private AccountDS accountDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDSTest.class);

	@Test
	@Transactional(value = "localTxManager", readOnly = true)
	public void testGetAccount() throws Exception {
		AccountDto account = accountDs.getAccountById(1L);
		LOGGER.info("account:{} ", account);
	}

	@Test
	@Transactional(value = "localTxManager", readOnly = false)
	public void testCreateAccount() throws Exception {
		AccountDto account = new AccountDto();
		account.setAccountNo("222222");
		account.setSecurityNo("222");
		account.setExpireDate("2019-10-10");
		account.setPaymode("credit");
		account.setBalance(new BigDecimal(1000.00));
		Long accountId = accountDs.createAccount(account);

		account = accountDs.getAccountById(accountId);
		LOGGER.info("account:{} ", account);
	}

	@Test
	@Transactional(value = "localTxManager", readOnly = false)
	public void testDeduct() throws Exception {
		AccountDto account = accountDs.getAccountById(1L);
		LOGGER.info("before deduct account:{} ", account);

		BillingTransactionDto billingTransaction = new BillingTransactionDto();
		billingTransaction.setBillingAmount(new BigDecimal(120));
		billingTransaction.setAccount(account);

		AccountTransactionRespDto accountAuthenticationDto = accountDs
				.deduct(billingTransaction);
		LOGGER.info("accountAuthenticationDto:{} ", accountAuthenticationDto);
		account = accountDs.getAccountById(1L);
		LOGGER.info("after deduct account:{} ", account);

		LOGGER.info("to test account transactions:{}");
		Set<BillingTransactionDto> transactions = accountDs
				.getAllTransactionsForAccount(account.getAccountNo(),
						account.getSecurityNo(), account.getPaymode());
		for (BillingTransactionDto billingTransactionDto : transactions) {
			LOGGER.info("billingTransactionDto:{} ", billingTransactionDto);
		}

	}

}
