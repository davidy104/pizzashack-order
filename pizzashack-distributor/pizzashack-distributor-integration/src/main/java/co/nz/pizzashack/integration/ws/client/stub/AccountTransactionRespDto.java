package co.nz.pizzashack.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for accountTransactionRespDto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="accountTransactionRespDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="transaction-no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="account-no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reasons" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="create-time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "account-transaction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accountTransactionRespDto", propOrder = { "accountId",
		"transactionNo", "accountNo", "code", "reasons", "createTime" })
public class AccountTransactionRespDto {

	protected Long accountId;
	@XmlElement(name = "transaction-no")
	protected String transactionNo;
	@XmlElement(name = "account-no")
	protected String accountNo;
	protected String code;
	protected String reasons;
	@XmlElement(name = "create-time")
	protected String createTime;

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
	 * Gets the value of the transactionNo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTransactionNo() {
		return transactionNo;
	}

	/**
	 * Sets the value of the transactionNo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTransactionNo(String value) {
		this.transactionNo = value;
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
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value) {
		this.code = value;
	}

	/**
	 * Gets the value of the reasons property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReasons() {
		return reasons;
	}

	/**
	 * Sets the value of the reasons property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReasons(String value) {
		this.reasons = value;
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
