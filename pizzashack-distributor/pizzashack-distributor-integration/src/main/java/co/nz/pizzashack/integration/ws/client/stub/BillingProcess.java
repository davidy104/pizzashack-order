package co.nz.pizzashack.integration.ws.client.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Java class for billingProcess complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="billingProcess">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ws.integration.billing.pizzashack.nz.co/}billingTransactionDto" minOccurs="0"/>
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
@XmlType(name = "billingProcess", propOrder = { "arg0" })
public class BillingProcess {

	protected BillingTransactionDto arg0;

	/**
	 * Gets the value of the arg0 property.
	 * 
	 * @return possible object is {@link BillingTransactionDto }
	 * 
	 */
	public BillingTransactionDto getArg0() {
		return arg0;
	}

	/**
	 * Sets the value of the arg0 property.
	 * 
	 * @param value
	 *            allowed object is {@link BillingTransactionDto }
	 * 
	 */
	public void setArg0(BillingTransactionDto value) {
		this.arg0 = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
