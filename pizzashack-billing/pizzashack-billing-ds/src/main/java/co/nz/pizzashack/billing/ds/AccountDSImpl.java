package co.nz.pizzashack.billing.ds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.nz.pizzashack.billing.NotFoundException;
import co.nz.pizzashack.billing.data.AccountModel;
import co.nz.pizzashack.billing.data.AccountModel.AccountType;
import co.nz.pizzashack.billing.data.AccountTransactionModel;
import co.nz.pizzashack.billing.data.AccountTransactionModel.TransType;
import co.nz.pizzashack.billing.data.converter.AccountConverter;
import co.nz.pizzashack.billing.data.converter.AccountTransConverter;
import co.nz.pizzashack.billing.data.dto.AccountDto;
import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto;
import co.nz.pizzashack.billing.data.dto.BillingTransactionDto;
import co.nz.pizzashack.billing.data.mapper.AccountMapper;
import co.nz.pizzashack.billing.data.mapper.AccountTransactionMapper;
import co.nz.pizzashack.billing.utils.GeneralUtils;

@Service
public class AccountDSImpl implements AccountDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDSImpl.class);

	@Resource
	private AccountTransactionMapper accountTransMapper;

	@Resource
	private AccountMapper accountMapper;

	@Resource
	private AccountConverter accountConverter;

	@Resource
	private AccountTransConverter accountTransConverter;

	@Override
	public Long createAccount(AccountDto account) throws Exception {
		LOGGER.info("createAccount start:{}", account);
		AccountModel model = accountConverter.toModel(account);
		model.setCreateTime(new Date());
		accountMapper.saveAccount(model);
		LOGGER.info("createAccount end:{}", model);
		return model.getAccountId();
	}

	@Override
	public AccountDto getAccountById(Long accountId) throws Exception {
		LOGGER.info("getAccountById start:{}", accountId);
		AccountDto found = null;
		AccountModel foundModel = accountMapper.getAccountById(accountId);
		if (foundModel == null) {
			throw new NotFoundException("Account not found by accountId["
					+ accountId + "] ");
		}
		LOGGER.info("found model:{} ", foundModel);
		found = accountConverter.toDto(foundModel);
		LOGGER.info("getAccountById end:{}", found);
		return found;
	}

	@Override
	public AccountDto getAccountByAccountNo(String accountNo, String securityNo)
			throws Exception {
		LOGGER.info("getAccountByAccountNo start:{}", accountNo);
		LOGGER.info("securityNo:{}", securityNo);
		AccountDto found = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountNo", accountNo);
		parameters.put("securityNo", securityNo);
		AccountModel foundModel = accountMapper.getAccounts(parameters).get(0);
		if (foundModel == null) {
			throw new NotFoundException("Account not found by no[" + accountNo
					+ "] and securityNo[" + securityNo + "]");
		}
		found = accountConverter.toDto(foundModel);
		LOGGER.info("getAccountByAccountNo end:{}", found);
		return found;
	}

	@Override
	public void deleteAccount(Long accountId) throws Exception {
		LOGGER.info("deleteAccount start:{}", accountId);
		accountMapper.deleteAccount(accountId);
		LOGGER.info("deleteAccount end:{}");
	}

	@Override
	public AccountTransactionRespDto deduct(BillingTransactionDto billingTrans)
			throws Exception {
		LOGGER.info("deduct start:{} ", billingTrans);

		AccountDto account = billingTrans.getAccount();
		AccountTransactionRespDto result = this.accountAuthentication(account);
		BigDecimal deductAmount = billingTrans.getBillingAmount();
		LOGGER.info("deductAmount:{} ", deductAmount);
		Date createTime = new Date();
		AccountModel model = null;
		BigDecimal newBalance = null;
		result.setCreateTime(GeneralUtils.dateToStr(createTime));
		result.setAccountNo(billingTrans.getAccount().getAccountNo());

		if (result.getCode().equals("000")) {
			model = accountMapper.getAccountById(result.getAccountId());
			if (model.getBalance().compareTo(deductAmount) == -1) {
				result.setCode("004");
				result.setReasons("Account balance not enough");
			} else {
				String accountTransNo = UUID.randomUUID().toString();
				newBalance = model.getBalance().subtract(deductAmount);
				model.setBalance(newBalance);
				accountMapper.updateAccount(model);
				AccountTransactionModel transModel = AccountTransactionModel
						.getBuilder(accountTransNo, model,
								TransType.out.value(), deductAmount).build();
				transModel.setCreateTime(createTime);
				accountTransMapper.saveAccountTrans(transModel);
				result.setTransactionNo(accountTransNo);
			}
		}
		LOGGER.info("deduct end:{} ", result);
		return result;
	}

	@Override
	public AccountTransactionRespDto accountAuthentication(AccountDto account) {
		LOGGER.info("accountAuthentication start:{} ", account);

		AccountTransactionRespDto resultDto = new AccountTransactionRespDto();
		resultDto.setAccountNo(account.getAccountNo());
		resultDto.setCode("000");
		AccountModel foundModel = null;
		Date today = new Date();
		Long accountId = account.getAccountId();
		if (accountId != null) {
			foundModel = accountMapper.getAccountById(accountId);
		} else {
			String accountNo = account.getAccountNo();
			String securityNo = account.getSecurityNo();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("accountNo", accountNo);
			parameters.put("securityNo", securityNo);
			foundModel = accountMapper.getAccounts(parameters).get(0);
		}

		if (foundModel == null) {
			resultDto.setCode("001");
			resultDto.setReasons("Account not found");
		} else {
			resultDto.setAccountId(foundModel.getAccountId());
			if (today.after(foundModel.getExpireDate())) {
				resultDto.setCode("003");
				resultDto.setReasons("Account expired");
			} else if (foundModel.getBalance().compareTo(BigDecimal.ZERO) != 1) {
				resultDto.setCode("002");
				resultDto.setReasons("Account balance is zero");
			}
		}
		LOGGER.info("accountAuthentication end:{} ", resultDto);
		return resultDto;
	}
	@Override
	public Set<BillingTransactionDto> getAllTransactionsForAccount(
			String accountNo, String securityNo, String accountTypeStr)
			throws Exception {
		LOGGER.info("getAllTransactionsForAccount start:{}");
		LOGGER.info("accountNo:{}", accountNo);
		LOGGER.info("securityNo:{}", securityNo);
		LOGGER.info("accountTypeStr:{}", accountTypeStr);
		Set<BillingTransactionDto> transactionSet = null;
		AccountModel foundModel = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountNo", accountNo);
		parameters.put("securityNo", securityNo);

		if (!StringUtils.isEmpty(accountTypeStr)) {
			if (accountTypeStr.equals("credit")) {
				parameters.put("accountType", AccountType.credit.value());
			} else if (accountTypeStr.equals("debit")) {
				parameters.put("accountType", AccountType.debit.value());
			}
		}

		foundModel = accountMapper.getAssociatedAccountTransactions(parameters)
				.get(0);

		if (foundModel == null) {
			throw new NotFoundException("Account not found by no[" + accountNo
					+ "] and securityNo[" + securityNo + "]");
		}

		Set<AccountTransactionModel> transactions = foundModel.getHistories();

		if (transactions != null && transactions.size() > 0) {
			transactionSet = new HashSet<BillingTransactionDto>();
			for (AccountTransactionModel accountTransaction : transactions) {
				accountTransaction.setAccount(foundModel);
				transactionSet.add(accountTransConverter
						.toDto(accountTransaction));
			}
		}
		return transactionSet;
	}

	@Override
	public BillingTransactionDto getBillingTransactionByTransNo(String transNo)
			throws Exception {
		LOGGER.info("getBillingTransactionByTransNo start:{} ", transNo);
		BillingTransactionDto billingTransactionDto = null;
		AccountTransactionModel accountTransactionModel = accountTransMapper
				.getAccountTransactionByTransNo(transNo);
		if (accountTransactionModel == null) {
			throw new NotFoundException(
					"accountTransactionModel not found by no[" + transNo + "]");
		}
		billingTransactionDto = accountTransConverter
				.toDto(accountTransactionModel);
		LOGGER.info("getBillingTransactionByTransNo end:{} ",
				billingTransactionDto);
		return billingTransactionDto;
	}

}
