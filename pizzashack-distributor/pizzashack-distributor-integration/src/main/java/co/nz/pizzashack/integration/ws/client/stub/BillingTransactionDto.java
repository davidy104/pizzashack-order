package co.nz.pizzashack.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for billingTransactionDto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="billingTransactionDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account-transaction-no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://ws.integration.billing.pizzashack.nz.co/}account" minOccurs="0"/>
 *         &lt;element name="transaction-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="billing-amount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="create-time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "billingTransactionDto", propOrder = { "accountTransactionNo",
		"account", "transactionType", "billingAmount", "createTime" })
public class BillingTransactionDto {

	@XmlElement(name = "account-transaction-no")
	protected String accountTransactionNo;
	@XmlElement
	protected AccountDto account;
	@XmlElement(name = "transaction-type")
	protected String transactionType;
	@XmlElement(name = "billing-amount")
	protected String billingAmount;
	@XmlElement(name = "create-time")
	protected String createTime;

	/**
	 * Gets the value of the accountTransactionNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAccountTransactionNo() {
		return accountTransactionNo;
	}

	/**
	 * Sets the value of the accountTransactionNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAccountTransactionNo(String value) {
		this.accountTransactionNo = value;
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

	/**
	 * Gets the value of the transactionType property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets the value of the transactionType property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTransactionType(String value) {
		this.transactionType = value;
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
	 * Gets the value of the createTime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the value of the createTime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCreateTime(String value) {
		this.createTime = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
