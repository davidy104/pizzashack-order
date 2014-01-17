package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.UserDto
import co.nz.pizzashack.data.model.RoleModel
import co.nz.pizzashack.data.model.UserModel
import co.nz.pizzashack.data.model.UserRoleModel
import co.nz.pizzashack.utils.GeneralUtils

@Component
@Slf4j
class UserConverter implements GeneralConverter<UserDto, UserModel> {

	@Resource
	RoleConverter roleConverter

	@Override
	UserDto toDto(UserModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		UserDto dto = new UserDto(username:model.username, password:model.password)
		if (model.createTime) {
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}
		dto.userId = model.userId

		if(loadStrategies && loadStrategies.length == 1){
			boolean ifLoadRoles = (Boolean)loadStrategies[0]
			if(ifLoadRoles && model.getUserRoleModels()){
				model.getUserRoleModels().each {UserRoleModel userRoleModel->
					RoleModel roleModel = userRoleModel.getRoleModel()
					dto.addRole(roleConverter.toDto(roleModel))
				}
			}
		}


		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	UserModel toModel(UserDto dto)  {
		log.info "toModel start:{} $dto"
		String encodePwd = GeneralUtils.pwdEncode(dto.password)
		UserModel model = UserModel.getBuilder(dto.username,
				encodePwd).build()
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
