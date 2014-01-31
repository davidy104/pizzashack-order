package co.nz.pizzashack.client.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for billingDto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="billingDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingRequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billingAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://ws.integration.pizzashack.nz.co/}account" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "billingRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billingDto", propOrder = { "billingRequestId", "orderNo",
		"billingCode", "billingMessage", "billingTime", "billingAmount",
		"account" })
public class BillingDto {

	protected String billingRequestId;
	protected String orderNo;
	protected String billingCode;
	protected String billingMessage;
	protected String billingTime;
	protected String billingAmount;
	@XmlElement(namespace = "http://ws.integration.pizzashack.nz.co/")
	protected AccountDto account;

	/**
	 * Gets the value of the billingRequestId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillingRequestId() {
		return billingRequestId;
	}

	/**
	 * Sets the value of the billingRequestId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillingRequestId(String value) {
		this.billingRequestId = value;
	}

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

	/**
	 * Gets the value of the billingTime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillingTime() {
		return billingTime;
	}

	/**
	 * Sets the value of the billingTime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillingTime(String value) {
		this.billingTime = value;
	}

	/**
	 * Gets the value of the billingAmount property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBillingAmount() {
		return billingAmount;
	}

	/**
	 * Sets the value of the billingAmount property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBillingAmount(String value) {
		this.billingAmount = value;
	}

	/**
	 * Gets the value of the account property.
	 * 
	 * @return possible object is {@link AccountDto }
	 * 
	 */
	public AccountDto getAccount() {
		return account;
	}

	/**
	 * Sets the value of the account property.
	 * 
	 * @param value
	 *            allowed object is {@link AccountDto }
	 * 
	 */
	public void setAccount(AccountDto value) {
		this.account = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
