package co.nz.pizzashack.client.test;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.nz.pizzashack.client.config.ApplicationConfiguration;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.data.dto.PizzashackDto;
import co.nz.pizzashack.client.ds.OrderDS;
import co.nz.pizzashack.client.ds.PizzashackDS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Ignore("run when service is ready")
public class PizzashackRestTest {

	@Resource
	private PizzashackDS pizzashackDs;
	@Resource
	private OrderDS orderDs;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PizzashackRestTest.class);

	@Test
	public void testGetAllPizzashacks() throws Exception {
		Set<PizzashackDto> pizzashacks = pizzashackDs.pizzashackItems();
		for (PizzashackDto pizzashackDto : pizzashacks) {
			LOGGER.info("pizzashackDto:{} ", pizzashackDto);
		}
	}

	@Test
	public void testGetPizzashackById() throws Exception {
		PizzashackDto found = pizzashackDs.getPizzashackDtoById(1L);
		LOGGER.info("found:{} ", found);
	}

	@Test
	public void testGetOrderByNo() throws Exception {
		OrderDto order = orderDs
				.getOrderByNo("6a0ad17b-f050-4fb5-964c-bd3b163b0ce8");
		LOGGER.info("order:{} ", order);
	}

	@Test
	public void testGetOrdersByEmail() throws Exception {
		String email = "david.yuan124@gmail.com";
		Set<OrderDto> orders = orderDs.getOrdersByCustomer(email);
		for (OrderDto order : orders) {
			LOGGER.info("order:{} ", order);
		}
	}

}
