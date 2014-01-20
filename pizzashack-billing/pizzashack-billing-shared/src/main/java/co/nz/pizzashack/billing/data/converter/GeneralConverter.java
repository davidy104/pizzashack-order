package co.nz.pizzashack.billing.data.converter;

import co.nz.pizzashack.billing.ConvertException;

public interface GeneralConverter<M, V> {

	M toDto(V model, Object... loadStrategies) throws ConvertException;

	V toModel(M dto, Object... additionalMappingSource) throws ConvertException;
}
