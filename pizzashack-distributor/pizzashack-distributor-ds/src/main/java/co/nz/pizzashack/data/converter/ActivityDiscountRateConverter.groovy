package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Component;

import co.nz.pizzashack.ConvertException;
import co.nz.pizzashack.data.dto.ActivityDiscountRateDto
import co.nz.pizzashack.data.model.ActivityDiscountRateModel
import co.nz.pizzashack.utils.GeneralUtils;

@Component
@Slf4j
class ActivityDiscountRateConverter implements GeneralConverter<ActivityDiscountRateDto, ActivityDiscountRateModel> {

	@Override
	ActivityDiscountRateDto toDto(ActivityDiscountRateModel model,
			Object... loadStrategies)  {
		log.info "toDto start:{} $model"
		ActivityDiscountRateDto dto = new ActivityDiscountRateDto(rateId:model.activityRateId,activityCode:model.activityCode,
		description:model.description,rate:model.rate,pizzaName:model.pizzashack.pizzaName)

		if(model.effectiveTime){
			dto.effectTime = GeneralUtils.dateToStr(model.effectiveTime)
		}

		if(model.expireTime){
			dto.expireTime = GeneralUtils.dateToStr(model.expireTime)
		}

		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	ActivityDiscountRateModel toModel(ActivityDiscountRateDto dto,
			Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		ActivityDiscountRateModel model = new ActivityDiscountRateModel(activityCode:dto.activityCode,description:dto.description,
		rate:dto.rate)

		if(dto.effectTime){
			model.effectiveTime = GeneralUtils.strToDate(dto.effectTime)
		}


		if(dto.expireTime){
			model.expireTime = GeneralUtils.strToDate(dto.expireTime)
		}
		log.info "toModel end:{} $model"
		return model
	}
}
