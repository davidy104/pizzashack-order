package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.RoleDto
import co.nz.pizzashack.data.model.RoleModel
import co.nz.pizzashack.utils.GeneralUtils

@Component
@Slf4j
class RoleConverter implements GeneralConverter<RoleDto, RoleModel> {

	@Override
	RoleDto toDto(RoleModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		RoleDto dto = new RoleDto(roleName:model.roleName,description:model.description)
		if(model.createTime){
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}
		dto.roleId = model.roleId
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	RoleModel toModel(RoleDto dto) {
		log.info "toModel start:{} $dto"
		RoleModel model = RoleModel.getBuilder(dto.roleName, dto.description).build()
		if(dto.createTime){
			model.createTime = GeneralUtils.strToDate(dto.createTime)
		}
		log.info "toModel end:{} $model"
		return model
	}
}
