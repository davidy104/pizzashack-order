package co.nz.pizzashack.test;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import co.nz.pizzashack.config.ApplicationConfiguration;
import co.nz.pizzashack.data.StaffLoadStrategies;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.DepartmentDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.data.dto.StaffDto;
import co.nz.pizzashack.data.dto.UserDto;
import co.nz.pizzashack.ds.OrderDS;
import co.nz.pizzashack.ds.OrderProcessDS;
import co.nz.pizzashack.ds.StaffDS;
import co.nz.pizzashack.ds.UserDS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class })
@WebAppConfiguration
// @Ignore
public class OrderProcessTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderProcessTest.class);

	@Resource
	private OrderProcessDS orderProcessDs;

	@Resource
	private OrderDS orderDs;

	@Resource
	private UserDS userDs;

	@Resource
	private StaffDS staffDs;

	private UserDto operator;
	private StaffDto davidReviewer;
	private StaffDto bradReviewer;

	@Before
	public void initialize() throws Exception {
		davidReviewer = staffDs
				.getStaffsByName("David", "Yuan", StaffLoadStrategies.LOAD_ALL)
				.iterator().next();
		bradReviewer = staffDs
				.getStaffsByName("Brad", "Wu", StaffLoadStrategies.LOAD_ALL)
				.iterator().next();

		operator = userDs.getUserByName("general");

		Set<DepartmentDto> departments = davidReviewer.getDepartments();
		for (DepartmentDto department : departments) {
			LOGGER.info("department:{} ", department);
		}
	}

	@Test
	public void testStartflow() throws Exception {
		OrderProcessDto orderProcess = orderProcessDs
				.startOrderProcess(operator);
		LOGGER.info("after start instance:{} ", orderProcess);
	}

	@Test
	public void testAutoReviewPassedCase() throws Exception {
		OrderProcessDto orderProcess = orderProcessDs
				.startOrderProcess(operator);
		String orderNo = orderProcess.getOrder().getOrderNo();
		LOGGER.info("after start instance:{} ", orderProcess);

		printAvailableOrderTasks(operator);

		OrderDto order = OrderTestUtils.mockAutoPassOrder(orderNo);
		LOGGER.info("order pazza type size:{} ", order.getOrderDetailsSet()
				.size());
		orderProcess = orderProcessDs.dataEntry(orderNo, order, operator);
		LOGGER.info("after dataentry:{} ", orderProcess);
		LOGGER.info("current pending activity:{} ",
				orderProcess.getPendingActivity());

		// printAvailableOrderTasks(operator);
		// printAvailableOrderTasks(davidReviewer.getUser());
		// printAvailableOrderTasks(bradReviewer.getUser());

		OrderDto orderDto = orderDs.getOrderByOrderNo(orderNo);
		LOGGER.info("after dataentry latest order from db:{} ", orderDto);

		BillingDto billing = OrderTestUtils.mockBilling(orderNo,
				orderDto.getTotalPrice());
		orderProcess = orderProcessDs.fillinBillingAccount(orderNo, billing,
				operator);
		LOGGER.info("after billing entry:{} ", orderProcess);
	}

	@Test
	public void testManualReviewCase() throws Exception {
		ProcessActivityDto pendingActivity = null;
		OrderProcessDto orderProcess = orderProcessDs
				.startOrderProcess(operator);
		String orderNo = orderProcess.getOrder().getOrderNo();
		LOGGER.info("after start instance:{} ", orderProcess);

		printAvailableOrderTasks(operator);

		OrderDto order = OrderTestUtils.mockManualUWOrder(orderNo);
		LOGGER.info("order pazza type size:{} ", order.getOrderDetailsSet()
				.size());
		orderProcess = orderProcessDs.dataEntry(orderNo, order, operator);
		LOGGER.info("after dataentry:{} ", orderProcess);
		LOGGER.info("current pending activity:{} ",
				orderProcess.getPendingActivity());

		printAvailableOrderTasks(operator);
		printAvailableOrderTasks(davidReviewer.getUser());
		printAvailableOrderTasks(bradReviewer.getUser());

		OrderDto orderDto = orderDs.getOrderByOrderNo(orderNo);
		LOGGER.info("after dataentry latest order from db:{} ", orderDto);

		orderProcessDs.claimOrderReviewTask(orderNo, davidReviewer.getUser());
		LOGGER.info("after david claim task:{} ");
		printAvailableOrderTasks(operator);
		printAvailableOrderTasks(davidReviewer.getUser());
		printAvailableOrderTasks(bradReviewer.getUser());

		OrderReviewRecordDto orderReviewRecordDto = new OrderReviewRecordDto();
		orderReviewRecordDto.setContent("review passed");
		orderReviewRecordDto.setReviewer(davidReviewer);
		orderReviewRecordDto.setReviewResult("accept");
		orderProcess = orderProcessDs.manualOrderReview(orderNo,
				orderReviewRecordDto);

		pendingActivity = orderProcess.getPendingActivity();
		LOGGER.info("after manul underwriting pendingActivity:{} ",
				pendingActivity);

		// BillingDto billing = OrderTestUtils.mockBilling(orderNo,
		// orderDto.getTotalPrice());
		// orderProcess = orderProcessDs.fillinBillingAccount(orderNo, billing,
		// operator);
		// LOGGER.info("after billing entry:{} ", orderProcess);
	}

	private void printAvailableOrderTasks(UserDto user) throws Exception {
		LOGGER.info("printAvailableOrderTasks for user[" + user.getUsername()
				+ "] start:{} ");
		Set<OrderProcessDto> availableOrderProcesses = orderProcessDs
				.getAllTransTaskForCurrentUser(user);
		if (availableOrderProcesses != null) {
			LOGGER.info("availableOrderProcesses size:{} ",
					availableOrderProcesses.size());
			for (OrderProcessDto orderProcessDto : availableOrderProcesses) {
				LOGGER.info("orderProcessDto:{} ", orderProcessDto);
			}
		}

		LOGGER.info("printAvailableOrderTasks for user[" + user.getUsername()
				+ "] end:{} ");
	}

}
