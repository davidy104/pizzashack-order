package co.nz.pizzashack.client.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for accountDto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="accountDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expireDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountDto", propOrder = { "paymode", "accountNo",
		"expireDate", "securityNo" })
public class AccountDto {

	protected String paymode;
	protected String accountNo;
	protected String expireDate;
	protected String securityNo;

	/**
	 * Gets the value of the paymode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPaymode() {
		return paymode;
	}

	/**
	 * Sets the value of the paymode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPaymode(String value) {
		this.paymode = value;
	}

	/**
	 * Gets the value of the accountNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * Sets the value of the accountNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAccountNo(String value) {
		this.accountNo = value;
	}

	/**
	 * Gets the value of the expireDate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getExpireDate() {
		return expireDate;
	}

	/**
	 * Sets the value of the expireDate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setExpireDate(String value) {
		this.expireDate = value;
	}

	/**
	 * Gets the value of the securityNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSecurityNo() {
		return securityNo;
	}

	/**
	 * Sets the value of the securityNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSecurityNo(String value) {
		this.securityNo = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
