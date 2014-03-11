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

import co.nz.pizzashack.NotFoundException;
import co.nz.pizzashack.data.dto.PizzashackDto;
import co.nz.pizzashack.data.model.PizzashackModel;
import co.nz.pizzashack.data.repository.PizzashackRepository;
import co.nz.pizzashack.support.PizzashackCaculator;

import static co.nz.pizzashack.data.predicates.PizzashackPredicates.findByPizzashackName;

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
				results.add(this.toPizzashackDto(model));
			}
		}
		LOGGER.info("getAllItems end:{} ");
		return results;
	}

	@Override
	public PizzashackDto getPizzashackById(Long pizzashackId) throws Exception {
		LOGGER.info("getPizzashackById start:{} ", pizzashackId);
		PizzashackDto found = null;
		PizzashackModel pizzashackModel = pizzashackRepository
				.findOne(pizzashackId);
		if (pizzashackModel == null) {
			throw new NotFoundException("Pizzashack not found by id["
					+ pizzashackId + "]");
		}
		found = this.toPizzashackDto(pizzashackModel);
		LOGGER.info("getPizzashackById end:{} ", found);
		return found;
	}

	@Override
	public PizzashackDto getPizzashackByName(String pizzashackName)
			throws Exception {
		LOGGER.info("getPizzashackByName start:{} ", pizzashackName);
		PizzashackDto found = null;
		PizzashackModel pizzashackModel = pizzashackRepository
				.findOne(findByPizzashackName(pizzashackName));
		if (pizzashackModel == null) {
			throw new NotFoundException(
					"Pizzashack not found by pizzashackName[" + pizzashackName
							+ "]");
		}
		found = this.toPizzashackDto(pizzashackModel);
		LOGGER.info("getPizzashackByName end:{} ", found);
		return found;
	}

	private PizzashackDto toPizzashackDto(PizzashackModel pizzashackModel) {
		PizzashackDto dto = new PizzashackDto();
		dto.setDescription(pizzashackModel.getDescription());
		dto.setIcon(pizzashackModel.getIcon());
		dto.setPizzaName(pizzashackModel.getPizzaName());
		dto.setPizzashackId(pizzashackModel.getPizzashackId());
		dto.setPrice(pizzashackModel.getPrice());
		LOGGER.info("pizzashack:{} ", dto);

		BigDecimal discountRate = pizzashackCaculator
				.getDiscountForPizza(pizzashackModel);
		if (discountRate.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal discountPrice = pizzashackModel.getPrice().subtract(
					pizzashackModel.getPrice().multiply(discountRate));
			dto.setAfterDiscount(discountPrice);
		}
		return dto;
	}

}
