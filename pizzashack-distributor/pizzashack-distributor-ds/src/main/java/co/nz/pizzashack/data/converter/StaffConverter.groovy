package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.StaffLoadStrategies
import co.nz.pizzashack.data.dto.StaffDto
import co.nz.pizzashack.data.model.DepartmentModel
import co.nz.pizzashack.data.model.IndividualModel
import co.nz.pizzashack.data.model.StaffDepartmentModel
import co.nz.pizzashack.data.model.StaffModel
import co.nz.pizzashack.data.model.UserModel
import co.nz.pizzashack.data.model.StaffModel.StaffLevel
import co.nz.pizzashack.data.model.StaffModel.StaffRole
import co.nz.pizzashack.utils.GeneralUtils

@Component
@Slf4j
class StaffConverter implements GeneralConverter<StaffDto, StaffModel> {

	@Resource
	UserConverter userConverter

	@Resource
	DepartmentConverter departmentConverter

	@Override
	StaffDto toDto(StaffModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		IndividualModel individualModel = model.individual
		StaffDto dto =new StaffDto(staffId:model.staffId,
		firstName:individualModel.firstName,
		lastName:individualModel.lastName,
		identity:individualModel.identity,
		email:individualModel.email)

		if(model.createDate){
			dto.createDate = GeneralUtils.dateToStr(model.createDate,'yyyy-MM-dd')
		}

		if(model.role){
			switch(model.role) {
				case StaffRole.operator.value() :
					dto.role = 'operator'
					break

				case StaffRole.manager.value() :
					dto.role = 'manager'
					break

				default :
					dto.role = 'operator'
			}
		}

		if(model.level){
			switch(model.level) {
				case StaffLevel.junior.value() :
					dto.level = 'junior'
					break

				case StaffLevel.intermedior.value() :
					dto.level = 'intermedior'
					break

				case StaffLevel.senior.value() :
					dto.level = 'senior'
					break

				default :
					dto.level = 'junior'
			}
		}

		if(loadStrategies && loadStrategies.length > 0){
			for(Object loadStrategie : loadStrategies){
				StaffLoadStrategies staffLoadStrategies = (StaffLoadStrategies)loadStrategie
				switch(staffLoadStrategies){
					case StaffLoadStrategies.LOAD_ALL :
						loadUser(dto,model.user)
						loadDepartment(dto,model.staffDepartments)
						break

					case StaffLoadStrategies.LOAD_DEPARTMENT :
						loadDepartment(dto,model.staffDepartments)

					case StaffLoadStrategies.LOAD_USER :
						loadUser(dto,model.user)
				}
			}
		}

		log.info "toDto end:{} $dto"
		return dto
	}

	def loadUser={StaffDto staffDto,UserModel user->
		if(user){
			staffDto.user = userConverter.toDto(user)
		}
	}

	def loadDepartment={StaffDto staffDto,List<StaffDepartmentModel> staffDepartments ->
		if(staffDepartments){
			staffDepartments.each {StaffDepartmentModel staffDepartmentModel->
				DepartmentModel departmentModel = staffDepartmentModel.getDepartmentModel()
				staffDto.addDepartment(departmentConverter.toDto(departmentModel))
			}
		}
	}

	@Override
	StaffModel toModel(StaffDto dto,Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		StaffModel model
		IndividualModel individual = new IndividualModel(firstName:dto.firstName,lastName:dto.lastName,identity:dto.identity,email:dto.email)
		if(additionalMappingSource && additionalMappingSource.length >0){
			model = (StaffModel)additionalMappingSource[0]
			model.individual = individual
		} else {
			model = new StaffModel(individual:individual)
			if(dto.createDate){
				model.createDate = GeneralUtils.strToDate(dto.createDate, 'yyyy-MM-dd')
			}
		}


		if(dto.level){
			switch (dto.level){
				case "junior" :
					model.level = StaffLevel.junior.value()
					break
				case "intermedior" :
					model.level = StaffLevel.intermedior.value()
					break
				case "senior" :
					model.level = StaffLevel.senior.value()
					break
				default :
					model.level = StaffLevel.junior.value()
					break
			}
		}

		if(dto.role){
			switch (dto.role) {
				case "operator" :
					model.role = StaffRole.operator.value()
					break
				case "manager" :
					model.role = StaffRole.manager.value()
					break
				default :
					model.role = StaffRole.operator.value()
					break
			}
		}

		log.info "toModel end:{} $model"
		return model
	}
}
