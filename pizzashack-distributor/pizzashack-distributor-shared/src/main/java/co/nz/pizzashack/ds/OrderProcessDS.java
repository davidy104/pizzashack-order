package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.OrderReviewRecordDto;
import co.nz.pizzashack.data.dto.UserDto;

public interface OrderProcessDS {
	OrderProcessDto startOrderProcess(UserDto operator) throws Exception;

	OrderProcessDto dataEntry(String orderNo, OrderDto order, UserDto operator)
			throws Exception;

	OrderProcessDto fillinBillingAccount(String orderNo, BillingDto billing,
			UserDto operator) throws Exception;

	void claimOrderReviewTask(String orderNo, UserDto currentLoginUser)
			throws Exception;

	OrderProcessDto manualOrderReview(String orderNo,
			OrderReviewRecordDto reviewRecord) throws Exception;

	Set<OrderProcessDto> getAllTransTaskForCurrentUser(UserDto currentLoginUser)
			throws Exception;
}
