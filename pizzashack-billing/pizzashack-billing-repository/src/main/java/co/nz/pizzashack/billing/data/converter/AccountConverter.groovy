package co.nz.pizzashack.billing.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.billing.ConvertException
import co.nz.pizzashack.billing.data.dto.AccountDto
import co.nz.pizzashack.billing.data.model.AccountModel
import co.nz.pizzashack.billing.data.model.AccountModel.AccountType
import co.nz.pizzashack.billing.utils.GeneralUtils

@Component
@Slf4j
class AccountConverter implements GeneralConverter<AccountDto, AccountModel> {

	@Override
	AccountDto toDto(AccountModel model, Object... loadStrategies){
		log.info "toDto start:{} $model"
		AccountDto dto = new AccountDto(accountId:model.accountId,accountNo:model.accountNo,balance:model.balance,securityNo:model.securityNo);
		Integer accountType = model.getAccountType();

		if(accountType){
			switch(accountType) {
				case AccountType.credit.value() :
					dto.paymode = 'credit'
					break

				case AccountType.debit.value() :
					dto.paymode = 'debit'
					break

				default :
					dto.paymode = 'credit'
			}
		}

		if(model.expireDate){
			dto.expireDate = GeneralUtils.dateToStr(model.expireDate, "yyyy-MM-dd")
		}

		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	AccountModel toModel(AccountDto dto,
			Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		AccountModel model = new AccountModel(accountNo:dto.accountNo,securityNo:dto.securityNo,balance:dto.balance)

		if(dto.expireDate){
			model.expireDate = GeneralUtils.strToDate(dto.expireDate, "yyyy-MM-dd")
		}

		if(dto.paymode){
			if(dto.paymode == 'credit'){
				model.accountType = AccountType.credit.value()
			}else if(dto.paymode == 'debit'){
				model.accountType = AccountType.debit.value()
			}
		}

		log.info "toModel end:{} $model"
		return null
	}
}
