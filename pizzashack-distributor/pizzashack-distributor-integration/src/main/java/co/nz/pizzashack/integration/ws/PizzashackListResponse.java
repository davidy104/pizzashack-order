package co.nz.pizzashack.integration.ws;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import co.nz.pizzashack.data.dto.PizzashackDto;

@SuppressWarnings("serial")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PizzashackListResponse implements Serializable {
	@XmlElement
	private Set<PizzashackDto> pizzashacks;
	@XmlElement
	private Integer totalNumber;

	public Set<PizzashackDto> getPizzashacks() {
		return pizzashacks;
	}

	public void setPizzashacks(Set<PizzashackDto> pizzashacks) {
		this.pizzashacks = pizzashacks;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("totalNumber", totalNumber)
				.append("pizzashacks", pizzashacks).toString();
	}
}
