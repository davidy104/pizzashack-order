package co.nz.pizzashack.ds;

import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.data.converter.UserConverter;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.data.model.OrderProcessModel;
import co.nz.pizzashack.data.repository.OrderProcessRepository;
import co.nz.pizzashack.data.repository.OrderRepository;
import co.nz.pizzashack.data.repository.PizzashackRepository;
import co.nz.pizzashack.data.repository.WorkflowRepository;
import co.nz.pizzashack.wf.ActivitiFacade;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class OrderProcessDSImpl implements OrderProcessDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessDSImpl.class);

	@Resource
	private ActivitiFacade activitiFacade;

	@Resource
	private UserConverter userConverter;

	@Resource
	private TaskService taskService;

	@Resource
	private WorkflowRepository workflowRepository;

	@Resource
	private PizzashackRepository pizzashackRepository;

	@Resource
	private OrderProcessRepository orderProcessRepository;

	@Resource
	private OrderRepository orderRepository;
	
	public static final String PROCESS_DEFINITION_KEY = "orderlocalProcess";
	public static final String CATEGORY = "order";

	@Override
	@Transactional(value = "localTxManager", readOnly = false)
	public OrderProcessDto startOrderProcess(UserDto operator) throws Exception {
		LOGGER.info("startOrderProcess start:{}", operator);
		OrderProcessModel orderProcessModel = null;
		
		return null;
	}

	@Override
	public OrderProcessDto dataEntry(String orderNo, OrderDto order,
			UserDto operator) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderProcessDto fillinBillingAccount(String orderNo,
			BillingDto billing, UserDto operator) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderProcessDto claimOrderReviewTask(String orderNo,
			UserDto currentLoginUser) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderProcessDto manualOrderReview(String orderNo,
			OrderReviewRecordDto reviewRecord) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<OrderProcessDto> getAllTransTaskForCurrentUser(
			UserDto currentLoginUser) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
