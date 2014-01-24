package co.nz.pizzashack.ds;

import co.nz.pizzashack.data.dto.ActivityDiscountRateDto;

public interface ActivityDiscountRateDS {

	ActivityDiscountRateDto getActivityDiscountRateById(
			Long activityDiscountRateId) throws Exception;

	ActivityDiscountRateDto getActivityDiscountRateByCode(
			String activityDiscountRateCode) throws Exception;

	void createActivityDiscountRate(ActivityDiscountRateDto activityDiscountRate)
			throws Exception;

	void updateActivityDiscountRate(Long activityDiscountRateId,
			ActivityDiscountRateDto activityDiscountRate) throws Exception;

	void deleteActivityDiscountRate(Long activityDiscountRateId)
			throws Exception;

}
