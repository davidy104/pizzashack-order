package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.ORDER_MAIN_PROCESS_OBJ;
import static co.nz.pizzashack.DistributorConstants.REVIEW_MAIN_PROCESS_OBJ;

import java.util.Date;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.converter.OrderReviewRecordConverter;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.model.OrderModel.OrderStatus;
import co.nz.pizzashack.data.model.OrderModel.PaymentStatus;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.model.OrderReviewRecordModel;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.data.repository.OrderReviewRecordRepository;
import co.nz.pizzashack.utils.GeneralUtils;

@Component("endExecutionListener")
public class EndExecutionListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EndExecutionListener.class);

	@Resource
	private OrderReviewRecordConverter orderReviewRecordConverter;

	@Resource
	private OrderReviewRecordRepository orderReviewRecordRepository;

	@Resource
	private OrderProcessRepository orderProcessRepository;

	@Resource
	private OrderRepository orderRepository;

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("EndExecutionListener start:{} ");
		OrderProcessDto orderProcess = (OrderProcessDto) execution
				.getVariable(ORDER_MAIN_PROCESS_OBJ);
		Date completedDate = new Date();
		orderProcess.setCompleteTime(GeneralUtils.dateToStr(completedDate));

		// synchronize OrderProcess, update completed date
		OrderProcessModel orderProcessModel = orderProcessRepository
				.findOne(orderProcess.getOrderProcessId());
		orderProcessModel.setCompleteTime(completedDate);

		// synchronize Order, update status
		OrderDto order = orderProcess.getOrder();
		OrderModel orderModel = orderRepository.findOne(order.getOrderId());
		String status = order.getStatus();
		if (status.equals("rejected")) {
			LOGGER.info("update order as rejected:{} ");
			orderModel.setStatus(OrderStatus.rejected.value());
			OrderReviewRecordDto orderReviewRecord = (OrderReviewRecordDto) execution
					.getVariable(REVIEW_MAIN_PROCESS_OBJ);
			OrderReviewRecordModel orderReviewRecordModel = orderReviewRecordConverter
					.toModel(orderReviewRecord);
			orderReviewRecordModel.setCreateTime(new Date());
			orderReviewRecordModel.setOrder(orderModel);
			orderReviewRecordRepository.save(orderReviewRecordModel);
		} else if (status.equals("delivered")) {
			LOGGER.info("update order as delivered:{} ");
			orderModel.setStatus(OrderStatus.delivered.value());
			orderModel.setPaymentStatus(PaymentStatus.paied.value());
		}

		LOGGER.info("EndExecutionListener end:{} ");
	}
}
