package co.nz.pizzashack.wf;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.model.OrderModel;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.ds.OrderProcessAccessor;

import static co.nz.pizzashack.DistributorConstants.ORDER_SUB_PROCESS_OBJ;

@Component("caculationMergeExecutionListener")
public class CaculationMergeExecutionListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaculationMergeExecutionListener.class);

	@Resource
	private OrderProcessAccessor orderProcessAccessor;

	@Resource
	private OrderRepository orderRepository;

	public void execute(ExecutionListenerExecution execution) throws Exception {
		LOGGER.info("CaculationMergeExecutionListener start");
		OrderProcessDto orderProcess = (OrderProcessDto) execution
				.getVariable(ORDER_SUB_PROCESS_OBJ);
		LOGGER.info("after calculation, orderProcess:{} ", orderProcess);

		OrderDto order = orderProcess.getOrder();
		OrderModel orderModel = orderRepository.findOne(order.getOrderId());
		orderProcessAccessor.mergeCalculationResult(order, orderModel);
		LOGGER.info("CaculationMergeExecutionListener end");
	}

}
