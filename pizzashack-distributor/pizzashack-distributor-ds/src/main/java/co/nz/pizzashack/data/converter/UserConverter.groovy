package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.UserDto
import co.nz.pizzashack.data.model.UserModel
import co.nz.pizzashack.utils.GeneralUtils

@Component
@Slf4j
class UserConverter implements GeneralConverter<UserDto, UserModel> {

	@Override
	UserDto toDto(UserModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		UserDto dto = new UserDto(username:model.username, password:model.password)
		if (model.createTime) {
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}
		dto.userId = model.userId
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	UserModel toModel(UserDto dto,Object... additionalMappingSource)  {
		log.info "toModel start:{} $dto"
		String encodePwd = GeneralUtils.pwdEncode(dto.password)
		UserModel model = new UserModel(username:dto.username,password:encodePwd)
		if (dto.createTime) {
			model.createTime = GeneralUtils.strToDate(dto.createTime)
		}

		if (dto.userId) {
			model.userId = dto.userId
		}
		log.info "toModel end:{} $model"
		return model
	}
}
