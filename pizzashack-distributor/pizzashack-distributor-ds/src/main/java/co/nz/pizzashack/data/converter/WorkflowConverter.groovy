package co.nz.pizzashack.data.converter;

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.WorkflowDto
import co.nz.pizzashack.data.model.WorkflowModel
import co.nz.pizzashack.utils.GeneralUtils

@Component
@Slf4j
class WorkflowConverter
implements
GeneralConverter<WorkflowDto, WorkflowModel> {

	@Override
	WorkflowDto toDto(WorkflowModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		WorkflowDto dto = new WorkflowDto(name:model.name,category:model.category,deployId:model.deployId,
		processDefinitionKey:model.processDefinitionKey,processDefinitionId:model.processDefinitionId);
		if(model.createTime){
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}
		dto.wfId = model.wfId
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	WorkflowModel toModel(WorkflowDto dto)  {
		log.info "toModel start:{} $dto"
		WorkflowModel model = WorkflowModel.getBuilder(dto.name,
				dto.category).build();
		if (dto.createTime) {
			model.createTime = GeneralUtils.strToDate(dto.createTime)
		}
		log.info "toModel end:{} $model"
		return model
	}
}
