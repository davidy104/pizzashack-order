package co.nz.pizzashack.ds;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.nz.pizzashack.data.dto.PizzashackDto;
import co.nz.pizzashack.data.model.PizzashackModel;
import co.nz.pizzashack.data.repository.PizzashackRepository;

@Service
@Transactional(value = "localTxManager", readOnly = true)
public class PizzashackDSImpl implements PizzashackDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackDSImpl.class);

	private PizzashackRepository pizzashackRepository;

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
				dto.setPrice(dto.getPrice());
				results.add(dto);
			}
		}
		LOGGER.info("getAllItems end:{} ");
		return results;
	}

}
