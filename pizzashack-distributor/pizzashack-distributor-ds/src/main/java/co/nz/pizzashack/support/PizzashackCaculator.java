package co.nz.pizzashack.support;

import static co.nz.pizzashack.data.predicates.PizzashackPredicates.findByPizzashackName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.dto.OrderDetailsDto;
import co.nz.pizzashack.data.dto.OrderDto;
import co.nz.pizzashack.data.model.ActivityDiscountRateModel;
import co.nz.pizzashack.data.model.PizzashackModel;
import co.nz.pizzashack.data.repository.PizzashackRepository;

@Component("pizzashackCaculator")
public class PizzashackCaculator {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackCaculator.class);

	@Resource
	private PizzashackRepository pizzashackRepository;

	public void apCaculate(OrderDto order) throws Exception {
		LOGGER.info("caculate start:{} ", order);
		Set<OrderDetailsDto> orderDetailsSet = order.getOrderDetailsSet();
		BigDecimal totalPrice = BigDecimal.ZERO;
		Integer totalNumber = 0;
		if (orderDetailsSet != null) {
		
			for (OrderDetailsDto orderDetails : orderDetailsSet) {
				String pizzaName = orderDetails.getPizzaName();
				int orderCount = orderDetails.getQty();
				totalNumber += orderCount;
				PizzashackModel pModel = pizzashackRepository
						.findOne(findByPizzashackName(pizzaName));
				if (pModel != null) {
					BigDecimal discountRate = this.getDiscountForPizza(pModel);
					BigDecimal price = pModel.getPrice();

					if (discountRate.compareTo(BigDecimal.ZERO) == 1) {
						price = price.subtract(price.multiply(discountRate));
					}

					BigDecimal totalPricePerPizza = price
							.multiply(new BigDecimal(orderCount));
					orderDetails.setTotalPrice(totalPricePerPizza);
					totalPrice = totalPrice.add(totalPricePerPizza);
				}
			}
			order.setQty(totalNumber);
			order.setTotalPrice(totalPrice);
		}
		LOGGER.info("caculate end:{} ", order);
	}

	public BigDecimal getDiscountForPizza(PizzashackModel model) {
		BigDecimal maxDiscount = BigDecimal.ZERO;
		Date today = new Date();
		Set<ActivityDiscountRateModel> rateSet = model
				.getActivityDiscountRateSet();
		if (rateSet != null) {
			for (ActivityDiscountRateModel activityDiscountRate : rateSet) {
				Date expireTime = activityDiscountRate.getExpireTime();
				Date effectiveTime = activityDiscountRate.getEffectiveTime();

				if (today.before(expireTime) && today.after(effectiveTime)) {
					if (activityDiscountRate.getRate().compareTo(maxDiscount) == 1) {
						maxDiscount = activityDiscountRate.getRate();
					}
				}
			}
		}
		return maxDiscount;
	}

}
