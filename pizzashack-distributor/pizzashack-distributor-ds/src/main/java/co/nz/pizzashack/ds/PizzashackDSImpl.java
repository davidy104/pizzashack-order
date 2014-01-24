package co.nz.pizzashack.ds;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.data.dto.PizzashackDto;
import co.nz.pizzashack.data.model.PizzashackModel;
import co.nz.pizzashack.data.repository.PizzashackRepository;
import co.nz.pizzashack.support.PizzashackCaculator;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class PizzashackDSImpl implements PizzashackDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackDSImpl.class);

	@Resource
	private PizzashackRepository pizzashackRepository;

	@Resource
	private PizzashackCaculator pizzashackCaculator;

	@Override
	public Set<PizzashackDto> getAllItems() throws Exception {
		LOGGER.info("getAllItems start:{} ");
		Set<PizzashackDto> results = null;
		List<PizzashackModel> modelList = pizzashackRepository.findAll();
		if (modelList != null && modelList.size() > 0) {
			results = new HashSet<PizzashackDto>();
			for (PizzashackModel model : modelList) {

				PizzashackDto dto = new PizzashackDto();
				dto.setDescription(model.getDescription());
				dto.setIcon(model.getIcon());
				dto.setPizzaName(model.getPizzaName());
				dto.setPizzashackId(model.getPizzashackId());
				dto.setPrice(model.getPrice());
				LOGGER.info("pizzashack:{} ", dto);

				BigDecimal discountRate = pizzashackCaculator
						.getDiscountForPizza(model);
				if (discountRate.compareTo(BigDecimal.ZERO) == 1) {
					BigDecimal discountPrice = model.getPrice().subtract(
							model.getPrice().multiply(discountRate));
					dto.setAfterDiscount(discountPrice);
				}

				results.add(dto);
			}
		}
		LOGGER.info("getAllItems end:{} ");
		return results;
	}

}
