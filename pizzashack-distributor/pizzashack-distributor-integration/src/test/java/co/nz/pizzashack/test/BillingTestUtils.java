package co.nz.pizzashack.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import co.nz.pizzashack.data.dto.AccountDto;
import co.nz.pizzashack.data.dto.BillingDto;
import co.nz.pizzashack.utils.GeneralUtils;

public class BillingTestUtils {

	public static BillingDto mockBilling() {
		String messageId = String.valueOf(getRandomNumber(5));
		BillingDto billingDto = new BillingDto();
		AccountDto account = new AccountDto();
		account.setAccountNo("111111");
		account.setSecurityNo("111");
		account.setPaymode("credit");
		account.setExpireDate("2019-06-24");
		billingDto.setAccount(account);
		billingDto.setBillingAmount(new BigDecimal(300.00));
		billingDto.setBillingRequestId(messageId);
		billingDto.setOrderNo("10001");
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
