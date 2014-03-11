package co.nz.pizzashack.client.integration.ws.client.pizzashack.stub;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for pizzashackDto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="pizzashackDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pizzashackId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="pizzaName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="afterDiscount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pizzashackDto", propOrder = { "pizzashackId", "pizzaName",
		"description", "price", "afterDiscount", "icon" })
public class PizzashackDto {

	protected Long pizzashackId;
	protected String pizzaName;
	protected String description;
	protected BigDecimal price;
	protected BigDecimal afterDiscount;
	protected String icon;

	/**
	 * Gets the value of the pizzashackId property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getPizzashackId() {
		return pizzashackId;
	}

	/**
	 * Sets the value of the pizzashackId property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setPizzashackId(Long value) {
		this.pizzashackId = value;
	}

	/**
	 * Gets the value of the pizzaName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPizzaName() {
		return pizzaName;
	}

	/**
	 * Sets the value of the pizzaName property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPizzaName(String value) {
		this.pizzaName = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the price property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the value of the price property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPrice(BigDecimal value) {
		this.price = value;
	}

	/**
	 * Gets the value of the afterDiscount property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getAfterDiscount() {
		return afterDiscount;
	}

	/**
	 * Sets the value of the afterDiscount property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setAfterDiscount(BigDecimal value) {
		this.afterDiscount = value;
	}

	/**
	 * Gets the value of the icon property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the value of the icon property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setIcon(String value) {
		this.icon = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
