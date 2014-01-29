package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;

public interface OrderProcessQueryDS {
	OrderProcessDto getOrderProcessByOrderNo(String orderNo) throws Exception;

	Set<OrderProcessDto> getOrderProcessesByCustomer(String customerEmail)
			throws Exception;

	OrderProcessDto getOrderProcessDtoById(Long orderProcessId)
			throws Exception;

	Set<ProcessActivityDto> getHistoryProcessActivitiesByOrderNo(String orderNo)
			throws Exception;
}
