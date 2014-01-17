package co.nz.pizzashack.data.converter;

import co.nz.pizzashack.ConvertException;

public interface GeneralConverter<M, V> {

	M toDto(V model, Object... loadStrategies) throws ConvertException;

	V toModel(M dto) throws ConvertException;
}
