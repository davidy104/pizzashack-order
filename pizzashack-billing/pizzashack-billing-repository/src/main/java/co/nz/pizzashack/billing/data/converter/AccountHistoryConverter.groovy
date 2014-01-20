package co.nz.pizzashack.billing.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.billing.ConvertException
import co.nz.pizzashack.billing.data.dto.AccountHistoryDto
import co.nz.pizzashack.billing.data.model.AccountHistoryModel
import co.nz.pizzashack.billing.data.model.AccountModel
import co.nz.pizzashack.billing.data.model.AccountHistoryModel.TransType
import co.nz.pizzashack.billing.utils.GeneralUtils

@Component
@Slf4j
class AccountHistoryConverter implements GeneralConverter<AccountHistoryDto, AccountHistoryModel> {

	@Override
	AccountHistoryDto toDto(AccountHistoryModel model,
			Object... loadStrategies)  {
		log.info "toDto start:{} $model"
		AccountHistoryDto dto  = new AccountHistoryDto(accountHistId:model.accountHistoryId,accountTransNo:model.accountTransNo,transAmount:model.transAmount)
		if(model.createTime){
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}

		Integer transType = model.transType
		if(transType){
			switch(transType){
				case TransType.in.value() :
					dto.transType='in'
					break

				case TransType.out.value() :
					dto.transType='out'
					break
			}
		}

		AccountModel accountModel = model.account
		dto.accountNo = accountModel.accountNo
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	AccountHistoryModel toModel(AccountHistoryDto dto,
			Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		AccountHistoryModel model = new AccountHistoryModel(transAmount:dto.transAmount)
		if(dto.transType){
			if(dto.transType == 'in'){
				model.transType = TransType.in.value()
			}else if(dto.transType == 'out'){
				model.transType = TransType.out.value()
			}
		}
		log.info "toModel end:{} $model"
		return model
	}
}
