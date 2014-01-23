package co.nz.pizzashack.client.test

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import co.nz.pizzashack.client.config.ApplicationConfiguration
import co.nz.pizzashack.client.data.dto.PizzashackDto
import co.nz.pizzashack.client.ds.PizzashackDS

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Ignore("run when service is ready")
class PizzashackRestTest {

	@Resource
	PizzashackDS pizzashackDs

	@Test
	void testGetAllPizzashacks() throws Exception{
		Set<PizzashackDto> pizzashacks = pizzashackDs.pizzashackItems()
		pizzashacks.each() {obj-> println "$obj" }
	}
}
