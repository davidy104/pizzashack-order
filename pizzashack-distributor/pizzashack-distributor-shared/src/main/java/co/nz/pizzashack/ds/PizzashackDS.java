package co.nz.pizzashack.ds;

import java.util.Set;

import co.nz.pizzashack.data.dto.PizzashackDto;

public interface PizzashackDS {

	Set<PizzashackDto> getAllItems() throws Exception;

	PizzashackDto getPizzashackById(Long pizzashackId) throws Exception;
}
