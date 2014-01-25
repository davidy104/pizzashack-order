package co.nz.pizzashack.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="account-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="account-no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expire-date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="security-no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "accountDto", propOrder = { "accountId", "accountType",
		"accountNo", "balance", "expireDate", "securityNo" })
public class AccountDto {

	protected Long accountId;
	@XmlElement(name = "account-type")
	protected String accountType;
	@XmlElement(name = "account-no")
	protected String accountNo;
	protected String balance;
	@XmlElement(name = "expire-date")
	protected String expireDate;
	@XmlElement(name = "security-no")
	protected String securityNo;

	/**
	 * Gets the value of the accountId property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * Sets the value of the accountId property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setAccountId(Long value) {
		this.accountId = value;
	}

	/**
	 * Gets the value of the accountType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * Sets the value of the accountType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAccountType(String value) {
		this.accountType = value;
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
	 * Gets the value of the balance property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * Sets the value of the balance property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBalance(String value) {
		this.balance = value;
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
