package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j

import org.springframework.stereotype.Component

import co.nz.pizzashack.ConvertException
import co.nz.pizzashack.data.dto.OrderDetailsDto
import co.nz.pizzashack.data.model.OrderModel
import co.nz.pizzashack.data.model.OrderPizzashackModel
import co.nz.pizzashack.data.model.PizzashackModel;

@Component
@Slf4j
class OrderDetailConverter implements GeneralConverter<OrderDetailsDto, OrderPizzashackModel> {

	@Override
	OrderDetailsDto toDto(OrderPizzashackModel model,
			Object... loadStrategies)  {
		log.info "toDto start:{} $model"
		OrderDetailsDto dto = new OrderDetailsDto(orderDetailId:model.orderPizzashackId,qty:model.qty,totalPrice:model.totalPrice)
		String pizzaName = model.pizzashackModel.pizzaName
		dto.pizzaName = pizzaName
		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	OrderPizzashackModel toModel(OrderDetailsDto dto,
			Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		OrderPizzashackModel model = new OrderPizzashackModel(qty:dto.qty,totalPrice:dto.totalPrice)

		OrderModel order = (OrderModel)additionalMappingSource[0]
		PizzashackModel pizza = (PizzashackModel)additionalMappingSource[1]

		model.orderModel=order
		model.pizzashackModel=pizza

		log.info "toModel end:{} $model"
		return model
	}
}
