package co.nz.pizzashack.client.utils;

import java.text.SimpleDateFormat

import javax.xml.bind.annotation.adapters.XmlAdapter

class DateAdapter extends XmlAdapter<String, Date> {

	SimpleDateFormat dateFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss")

	@Override
	Date unmarshal(String v) {
		return dateFormat.parse(v)
	}

	@Override
	String marshal(Date v)  {
		return dateFormat.format(v)
	}
}
