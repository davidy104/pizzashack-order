package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.DepartmentDto
import co.nz.pizzashack.data.model.DepartmentModel
import co.nz.pizzashack.utils.GeneralUtils;

@Component
@Slf4j
class DepartmentConverter implements GeneralConverter<DepartmentDto, DepartmentModel> {

	@Override
	DepartmentDto toDto(DepartmentModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		DepartmentDto dto = new DepartmentDto(deptId:model.deptId,deptName:model.deptName)
		if(model.createDate){
			dto.createDate = GeneralUtils.dateToStr(model.createDate, 'yyyy-MM-dd')
		}
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	DepartmentModel toModel(DepartmentDto dto) {
		log.info "toModel start:{} $dto"
		DepartmentModel model = new DepartmentModel(deptName:dto.deptName)
		if(dto.createDate){
			model.createDate = GeneralUtils.strToDate(dto.createDate, 'yyyy-MM-dd')
		}
		log.info "toModel end:{} $model"
		return model
	}
}
