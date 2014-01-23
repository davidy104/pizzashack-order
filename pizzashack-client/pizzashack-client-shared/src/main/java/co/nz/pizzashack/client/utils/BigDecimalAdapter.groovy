package co.nz.pizzashack.client.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter

class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	BigDecimal unmarshal(String s)  {
		return new BigDecimal(s)
	}

	@Override
	String marshal(BigDecimal value) {
		if (value != null) {
			return value.toString()
		}
		return null
	}
}
