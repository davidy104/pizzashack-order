package co.nz.pizzashack.billing.integration.validation

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Component;

import co.nz.pizzashack.billing.data.dto.AccountDto;

@Component
@Slf4j
class AccountServicesValidator {

	void accountAuthenticationValidation(AccountDto account) {
		if(!account.expireDate || !account.accountNo || !account.securityNo){
			throw new Exception('account information is not enough for authentication')
		}
	}

	void checkAccountTransactionsValidation(AccountDto account){
		if(!account.accountNo || !account.securityNo){
			throw new Exception('account information is not enough to get all its transactions')
		}
	}
}
