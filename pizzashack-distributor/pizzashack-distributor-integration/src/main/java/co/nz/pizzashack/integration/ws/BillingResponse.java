package co.nz.pizzashack.integration.ws;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("serial")
@XmlRootElement(name = "billingResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillingResponse implements Serializable {
	@XmlElement
	private String orderNo;
	@XmlElement
	private String billingCode;
	@XmlElement
	private String billingMessage;

	public BillingResponse() {
	}

	public BillingResponse(String orderNo) {
		super();
		this.orderNo = orderNo;
	}

	public BillingResponse(String orderNo, String billingCode) {
		this.orderNo = orderNo;
		this.billingCode = billingCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}

	public String getBillingMessage() {
		return billingMessage;
	}

	public void setBillingMessage(String billingMessage) {
		this.billingMessage = billingMessage;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.append("orderNo", orderNo).append("billingCode", billingCode)
				.append("billingMessage", billingMessage).toString();
	}
}
