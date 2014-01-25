package co.nz.pizzashack.data.converter;

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.OrderLoadStrategies
import co.nz.pizzashack.data.dto.OrderDto
import co.nz.pizzashack.data.model.CustomerModel
import co.nz.pizzashack.data.model.DepartmentModel
import co.nz.pizzashack.data.model.OrderModel
import co.nz.pizzashack.data.model.OrderPizzashackModel
import co.nz.pizzashack.data.model.PizzashackModel
import co.nz.pizzashack.data.model.OrderModel.OrderStatus
import co.nz.pizzashack.utils.GeneralUtils

@Component
@Slf4j
class OrderConverter implements GeneralConverter<OrderDto, OrderModel> {
	@Resource
	CustomerConverter customerConverter

	@Resource
	OrderDetailConverter orderDetailConverter

	@Override
	OrderDto toDto(OrderModel model, Object... loadStrategies) {
		log.info "toDto start:{} $model"
		OrderDto dto = new OrderDto(orderId:model.orderId,orderNo:model.orderNo,qty:model.quantity,
		totalPrice:model.totalPrice,address:model.shipAddress)

		if(model.status){
			switch(model.status) {
				case OrderStatus.dataEntry.value() :
					dto.status = 'dataEntry'
					break

				case OrderStatus.pendingOnBilling.value() :
					dto.status = 'pendingOnBilling'
					break

				case OrderStatus.pendingOnReview.value() :
					dto.status = 'pendingOnReview'
					break

				case OrderStatus.delivered.value() :
					dto.status = 'delivered'
					break

				case OrderStatus.rejected.value() :
					dto.status = 'rejected'
					break

				default :
					dto.status = 'dataEntry'
			}
		}

		if(model.orderTime){
			dto.orderTime = GeneralUtils.dateToStr(model.orderTime)
		}

		if(model.deliverTime){
			dto.deliverTime = GeneralUtils.dateToStr(model.deliverTime)
		}

		if(loadStrategies && loadStrategies.length > 0){
			for(Object loadStrategie : loadStrategies){
				OrderLoadStrategies orderLoadStrategies = (OrderLoadStrategies)loadStrategie
				switch(orderLoadStrategies){
					case orderLoadStrategies.LOAD_ALL :
						loadCustomer(dto,model.customer)
						loadOrderDetails(dto,model.orderPizzashackModels)
						break

					case orderLoadStrategies.LOAD_CUSTOMER :
						loadCustomer(dto,model.customer)
						break

					case orderLoadStrategies.LOAD_DETAILS :
						loadOrderDetails(dto,model.orderPizzashackModels)
						break
				}
			}
		}
		log.info "toDto end:{} $dto"
		return dto
	}

	def loadCustomer={OrderDto orderDto,CustomerModel customer->
		if(customer){
			orderDto.customer = customerConverter.toDto(customer)
		}
	}

	def loadOrderDetails={OrderDto orderDto,List<OrderPizzashackModel> orderPizzashackModels ->
		if(orderPizzashackModels){
			orderPizzashackModels.each {OrderPizzashackModel orderPizzashackModel->
				orderDto.addPizzaOrder(orderDetailConverter.toDto(orderPizzashackModel))
			}
		}
	}

	@Override
	OrderModel toModel(OrderDto dto, Object... additionalMappingSource){
		log.info "toModel start:{} $dto"
		OrderModel model = new OrderModel(orderNo:dto.orderNo,quantity:dto.qty,shipAddress:dto.address,totalPrice:dto.totalPrice)

		log.info "toModel end:{} $model"
		return model
	}
}
