package co.nz.pizzashack.wf;

import static co.nz.pizzashack.DistributorConstants.BILLING_MAIN_PROCESS_OBJ;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.repository.OrderProcessRepository;

@Component("orderDeliverHandler")
public class OrderDeliverHandler {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderDeliverHandler.class);

	@Resource
	private OrderProcessRepository orderProcessRepository;

	public void deliver(DelegateExecution execution) throws Exception {
		LOGGER.info("OrderDeliverHandler start:{}");
		OrderProcessDto orderProcess = (OrderProcessDto) execution
				.getVariable(BILLING_MAIN_PROCESS_OBJ);
		LOGGER.info("orderProcess:{}", orderProcess);
		OrderDto order = orderProcess.getOrder();
		CustomerDto customer = order.getCustomer();
		
		
	}
	
	
	private void updateOrderAsDelivered(OrderDto order){
		
	}
	
	
}
