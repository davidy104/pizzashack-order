package co.nz.pizzashack.data.converter

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Component;

import co.nz.pizzashack.ConvertException;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderReviewRecordModel
import co.nz.pizzashack.data.model.OrderReviewRecordModel.ReviewStatus;
import co.nz.pizzashack.utils.GeneralUtils;

@Component
@Slf4j
class OrderReviewRecordConverter implements GeneralConverter<OrderReviewRecordDto, OrderReviewRecordModel> {

	@Override
	OrderReviewRecordDto toDto(OrderReviewRecordModel model,
			Object... loadStrategies)  {
		log.info "toDto start:{} $model"
		OrderReviewRecordDto dto = new OrderReviewRecordDto(reviewRecordId:model.reviewRecordId,content:model.content)

		OrderModel orderModel = model.order
		dto.orderNo = orderModel.orderNo

		if(model.createTime){
			dto.createTime = GeneralUtils.dateToStr(model.createTime)
		}

		if(model.reviewStatus){
			switch(model.reviewStatus) {
				case ReviewStatus.accept.value() :
					dto.reviewResult = 'accept'
					break

				case ReviewStatus.pending.value() :
					dto.reviewResult = 'pending'
					break

				case ReviewStatus.reject.value() :
					dto.reviewResult = 'reject'
					break

				default :
					dto.reviewResult = 'pending'
			}
		}

		log.info "toDto end:{} $dto"
		return dto
	}

	@Override
	OrderReviewRecordModel toModel(OrderReviewRecordDto dto,
			Object... additionalMappingSource) {
		log.info "toModel start:{} $dto"
		OrderReviewRecordModel model = new OrderReviewRecordModel(content:dto.content)
		if(dto.createTime){
			model.createTime = GeneralUtils.strToDate(dto.createTime)
		}
		if(dto.reviewResult){
			switch (dto.reviewResult){
				case "reject" :
					model.reviewStatus = ReviewStatus.reject.value()
					break
				case "pending" :
					model.reviewStatus = ReviewStatus.pending.value()
					break
				case "accept" :
					model.reviewStatus = ReviewStatus.accept.value()
					break
				default :
					model.reviewStatus = ReviewStatus.pending.value()
			}
		}
		log.info "toModel end:{} $model"
		return model
	}
}
