package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.OrderLoadStrategies;
import co.nz.pizzashack.data.dto.OrderProcessDto
import co.nz.pizzashack.data.model.OrderProcessModel
import co.nz.pizzashack.utils.GeneralUtils;

@Component
@Slf4j
class OrderProcessConverter implements GeneralConverter<OrderProcessDto, OrderProcessModel> {

	@Resource
	OrderConverter orderConverter

	@Resource
	UserConverter userConverter

	@Override
	OrderProcessDto toDto(OrderProcessModel model,
			Object... loadStrategies)  {
		log.info "toDto start:{} $model"
		OrderProcessDto dto = new OrderProcessDto(orderProcessId:model.orderProcessId,
		mainProcessInstanceId:model.mainProcessInstanceId,mainProcessDefinitionId:model.mainProcessDefinitionId,
		activeProcesssInstanceId:model.activeProcesssInstanceId,activeProcessDefinitionId:model.activeProcessDefinitionId,
		executionId:model.executionId)

		if(model.createTime){
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}

		if(model.completeTime){
			dto.completeTime = GeneralUtils.dateToStr(model.completeTime)
		}

		if(model.order){
			dto.order = orderConverter.toDto(model.order, OrderLoadStrategies.LOAD_ALL)
		}

		if(model.operator){
			dto.operator = userConverter.toDto(model.operator)
		}
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	OrderProcessModel toModel(OrderProcessDto dto,
			Object... additionalMappingSource) {

		OrderProcessModel model = new OrderProcessModel(mainProcessInstanceId:dto.mainProcessInstanceId,
		mainProcessDefinitionId:dto.mainProcessDefinitionId,activeProcesssInstanceId:dto.activeProcesssInstanceId,
		activeProcessDefinitionId:dto.activeProcessDefinitionId,executionId:dto.executionId)

		if(dto.createTime){
			model.createTime = GeneralUtils.strToDate(dto.createTime)
		}

		if(dto.completeTime){
			model.completeTime = GeneralUtils.strToDate(dto.completeTime)
		}

		return model
	}
}
