package co.nz.pizzashack.client.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for billingResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="billingResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "billingResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billingResponse", propOrder = { "orderNo", "billingCode",
		"billingMessage" })
public class BillingResponse {

	protected String orderNo;
	protected String billingCode;
	protected String billingMessage;

	/**
	 * Gets the value of the orderNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the value of the orderNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOrderNo(String value) {
		this.orderNo = value;
	}

	/**
	 * Gets the value of the billingCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillingCode() {
		return billingCode;
	}

	/**
	 * Sets the value of the billingCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillingCode(String value) {
		this.billingCode = value;
	}

	/**
	 * Gets the value of the billingMessage property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillingMessage() {
		return billingMessage;
	}

	/**
	 * Sets the value of the billingMessage property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillingMessage(String value) {
		this.billingMessage = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
