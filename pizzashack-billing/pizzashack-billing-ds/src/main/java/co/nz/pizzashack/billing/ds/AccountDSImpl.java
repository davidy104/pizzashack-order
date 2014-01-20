package co.nz.pizzashack.billing.ds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.nz.pizzashack.billing.NotFoundException;
import co.nz.pizzashack.billing.data.converter.AccountConverter;
import co.nz.pizzashack.billing.data.converter.AccountHistoryConverter;
import co.nz.pizzashack.billing.data.dto.AccountAuthenticationDto;
import co.nz.pizzashack.billing.data.dto.AccountDto;
import co.nz.pizzashack.billing.data.dto.AccountHistoryDto;
import co.nz.pizzashack.billing.data.mapper.AccountHistoryMapper;
import co.nz.pizzashack.billing.data.mapper.AccountMapper;
import co.nz.pizzashack.billing.data.model.AccountHistoryModel;
import co.nz.pizzashack.billing.data.model.AccountHistoryModel.TransType;
import co.nz.pizzashack.billing.data.model.AccountModel;

@Service
public class AccountDSImpl implements AccountDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AccountDSImpl.class);

	@Resource
	private AccountHistoryMapper accountHistoryMapper;

	@Resource
	private AccountMapper accountMapper;

	@Resource
	private AccountConverter accountConverter;

	@Resource
	private AccountHistoryConverter accountHistoryConverter;

	@Override
	public void createAccount(AccountDto account) throws Exception {
		LOGGER.info("createAccount start:{}", account);
		AccountModel model = accountConverter.toModel(account);
		accountMapper.saveAccount(model);
		LOGGER.info("createAccount end:{}", model);
	}

	@Override
	public AccountDto getAccountByAccountNo(String accountNo, String securityNo)
			throws Exception {
		LOGGER.info("getAccountByAccountNo start:{}", accountNo);
		LOGGER.info("securityNo:{}", securityNo);
		AccountDto found = null;
		AccountModel foundModel = accountMapper
				.getAccountByAccountNoAndSecurityNo(accountNo, securityNo);
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
	public AccountAuthenticationDto deduct(AccountDto account,
			BigDecimal deductAmount) throws Exception {
		LOGGER.info("deduct start:{} ", account);
		LOGGER.info("deductAmount:{} ", deductAmount);
		AccountAuthenticationDto result = this.accountAuthentication(account);
		AccountModel model = null;
		BigDecimal newBalance = null;
		if (result.getCode().equals("000")) {
			model = accountMapper.getAccountById(result.getAccountId());
			if (deductAmount.compareTo(model.getBalance()) == -1) {
				result.setCode("004");
				result.setReasons("Account balance not enough");
			} else {
				newBalance = model.getBalance().subtract(deductAmount);
				model.setBalance(newBalance);
				accountMapper.updateAccount(model);
				String accountTransNo = UUID.randomUUID().toString();
				AccountHistoryModel historyModel = AccountHistoryModel
						.getBuilder(accountTransNo, model,
								TransType.out.value(), deductAmount).build();
				historyModel.setCreateTime(new Date());
				accountHistoryMapper.saveAccountHistory(historyModel);
			}
		}
		LOGGER.info("deduct end:{} ", result);
		return result;
	}

	@Override
	public AccountAuthenticationDto accountAuthentication(AccountDto account) {
		LOGGER.info("accountAuthentication start:{} ", account);
		AccountAuthenticationDto resultDto = new AccountAuthenticationDto();
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
			foundModel = accountMapper.getAccountByAccountNoAndSecurityNo(
					accountNo, securityNo);
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
	public Set<AccountHistoryDto> getAllHistoryForAccount(String accountNo,
			String securityNo, Integer accountType) throws Exception {

		return null;
	}

}
