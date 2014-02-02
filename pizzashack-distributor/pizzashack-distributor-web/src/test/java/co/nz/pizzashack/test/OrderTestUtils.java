package co.nz.pizzashack.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import co.nz.pizzashack.data.dto.AccountDto;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.data.dto.CustomerDto;
import co.nz.pizzashack.data.dto.OrderDetailsDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.utils.GeneralUtils;

public class OrderTestUtils {

	public static OrderDto mockAutoPassOrder(String orderNo) {
		// Spicy Italian,Chicken Parmesan,Garden Fresh
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderNo(orderNo);
		orderDto.setAddress("25 mavon downs");
		orderDto.setQty(6);

		OrderDetailsDto orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Chicken Parmesan");
		orderDetail.setQty(2);
		orderDto.addPizzaOrder(orderDetail);

		orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Spicy Italian");
		orderDetail.setQty(4);
		orderDto.addPizzaOrder(orderDetail);

		orderDto.setCustomer(mockCustomer());
		return orderDto;
	}

	/**
	 * maunual uw-> total order number more than 10 but less than 30
	 * 
	 * @param orderNo
	 * @return
	 */
	public static OrderDto mockManualUWOrder(String orderNo) {
		// Spicy Italian,Chicken Parmesan,Garden Fresh
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderNo(orderNo);
		orderDto.setAddress("25 mavon downs");
		orderDto.setQty(14);

		OrderDetailsDto orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Chicken Parmesan");
		orderDetail.setQty(10);
		orderDto.addPizzaOrder(orderDetail);

		orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Garden Fresh");
		orderDetail.setQty(4);
		orderDto.addPizzaOrder(orderDetail);

		orderDto.setCustomer(mockCustomer());
		return orderDto;
	}
	
	public static OrderDto mockRejectOrder(String orderNo) {
		// Spicy Italian,Chicken Parmesan,Garden Fresh
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderNo(orderNo);
		orderDto.setAddress("25 mavon downs");
		orderDto.setQty(31);

		OrderDetailsDto orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Chicken Parmesan");
		orderDetail.setQty(20);
		orderDto.addPizzaOrder(orderDetail);

		orderDetail = new OrderDetailsDto();
		orderDetail.setPizzaName("Garden Fresh");
		orderDetail.setQty(11);
		orderDto.addPizzaOrder(orderDetail);

		orderDto.setCustomer(mockCustomer());
		return orderDto;
	}

	public static CustomerDto mockCustomer() {
		CustomerDto customer = new CustomerDto();
		customer.setCustomerEmail("david.yuan124@gmail.com");
		customer.setCustomerName("david");
		return customer;
	}

	public static BillingDto mockBilling(String orderNo, BigDecimal ap) {
		String messageId = String.valueOf(getRandomNumber(5));
		BillingDto billingDto = new BillingDto();
		AccountDto account = new AccountDto();
		account.setAccountNo("111111");
		account.setSecurityNo("111");
		account.setPaymode("credit");
		account.setExpireDate("2019-06-24");
		billingDto.setAccount(account);
		billingDto.setBillingAmount(ap);
		billingDto.setBillingRequestId(messageId);
		billingDto.setOrderNo(orderNo);
		billingDto.setBillingTime(GeneralUtils.dateToStr(new Date()));
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
