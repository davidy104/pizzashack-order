package co.nz.pizzashack.client.ds;

import java.util.Set;

import co.nz.pizzashack.client.data.dto.PizzashackDto;
/**
 *
 * @author david
 *
 */
public interface PizzashackDS {

	Set<PizzashackDto> pizzashackItems() throws Exception;
}
