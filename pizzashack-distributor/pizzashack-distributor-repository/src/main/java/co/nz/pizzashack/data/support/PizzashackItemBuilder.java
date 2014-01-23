package co.nz.pizzashack.data.support;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import co.nz.pizzashack.data.model.PizzashackModel;

@Component
public class PizzashackItemBuilder extends EntityBuilder<PizzashackModel> {

	@Override
	void initProduct() {
	}

	public PizzashackItemBuilder create(String name, String description,
			BigDecimal price, String icon) {

		this.product = PizzashackModel.getBuilder(name, description, price,
				icon).build();
		return this;
	}

	@Override
	PizzashackModel assembleProduct() {
		return this.product;
	}

}
