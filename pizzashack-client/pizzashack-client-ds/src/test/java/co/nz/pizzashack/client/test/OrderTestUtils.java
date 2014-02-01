package co.nz.pizzashack.client.test;

import java.math.BigInteger;
import java.util.Random;

import co.nz.pizzashack.client.data.dto.CustomerDto;
import co.nz.pizzashack.client.data.dto.OrderDetailsDto;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.integration.ws.client.stub.AccountDto;
import co.nz.pizzashack.client.integration.ws.client.stub.BillingDto;

public class OrderTestUtils {

	public static OrderDto mockAutoPassOrder() {
		// Spicy Italian,Chicken Parmesan,Garden Fresh
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderRequestId(String.valueOf(getRandomNumber(5)));
		orderDto.setAddress("25 mavon downs");

		OrderDetailsDto orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Chicken Parmesan");
		orderDetail.setQty(2);
		orderDto.addOrderDetails(orderDetail);

		orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Spicy Italian");
		orderDetail.setQty(4);
		orderDto.addOrderDetails(orderDetail);

		orderDto.setCustomer(mockCustomer());
		return orderDto;
	}

	/**
	 * maunual uw-> total order number more than 10 but less than 30
	 * 
	 * @param orderNo
	 * @return
	 */
	public static OrderDto mockManualUWOrder() {
		// Spicy Italian,Chicken Parmesan,Garden Fresh
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderRequestId(String.valueOf(getRandomNumber(5)));
		orderDto.setAddress("25 mavon downs");

		OrderDetailsDto orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Chicken Parmesan");
		orderDetail.setQty(10);
		orderDto.addOrderDetails(orderDetail);

		orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Garden Fresh");
		orderDetail.setQty(4);
		orderDto.addOrderDetails(orderDetail);

		orderDto.setCustomer(mockCustomer());
		return orderDto;
	}

	public static CustomerDto mockCustomer() {
		CustomerDto customer = new CustomerDto();
		customer.setCustomerEmail("david.yuan124@gmail.com");
		customer.setCustomerName("david");
		return customer;
	}

	public static BillingDto mockBilling(String orderNo, String billingAmt) {
		BillingDto billingDto = new BillingDto();
		AccountDto account = new AccountDto();
		account.setAccountNo("111111");
		account.setSecurityNo("111");
		account.setPaymode("credit");
		account.setExpireDate("2019-06-24");
		billingDto.setAccount(account);
		billingDto.setBillingAmount(billingAmt);
		billingDto.setOrderNo(orderNo);
		return billingDto;
	}

	public static BigInteger getRandomNumber(final int digCount) {
		return getRandomNumber(digCount, new Random());
	}

	public static BigInteger getRandomNumber(final int digCount, Random rnd) {
		final char[] ch = new char[digCount];
		for (int i = 0; i < digCount; i++) {
			ch[i] = (char) ('0' + (i == 0 ? rnd.nextInt(9) + 1 : rnd
					.nextInt(10)));
		}
		return new BigInteger(new String(ch));
	}
}
