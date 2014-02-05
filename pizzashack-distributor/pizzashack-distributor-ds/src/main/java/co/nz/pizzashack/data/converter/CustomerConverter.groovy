package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.CustomerDto
import co.nz.pizzashack.data.model.CustomerModel

@Component
@Slf4j
class CustomerConverter implements GeneralConverter<CustomerDto, CustomerModel> {

	@Override
	CustomerDto toDto(CustomerModel model, Object... loadStrategies){
		log.info "toDto start:{} $model"
		if(!model){
			return null
		}
		CustomerDto dto = new CustomerDto(customerName:model.customerName,customerEmail:model.email)
		if(model.custId){
			dto.custId=model.custId
		}
		Integer credits = model.credits
		switch(credits){
			case 0..50 :
				dto.level = 'bronze'
				break
			case 51..100 :
				dto.level = 'silver'
				break
			case {it > 100} :
				dto.level = 'gold'
				break
			default:
				dto.level = 'bronze'
		}
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	CustomerModel toModel(CustomerDto dto,
			Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		CustomerModel model = new CustomerModel(customerName:dto.customerName,email:dto.customerEmail)
		log.info "toModel end:{} $model"
		return model
	}
}
