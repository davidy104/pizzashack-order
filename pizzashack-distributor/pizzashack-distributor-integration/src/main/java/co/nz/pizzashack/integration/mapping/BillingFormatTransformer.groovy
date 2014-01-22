package co.nz.pizzashack.integration.mapping

import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder

import org.springframework.stereotype.Component

import co.nz.pizzashack.data.dto.BillingDto

@Component
@Slf4j
class BillingFormatTransformer {

	/**
	 * <<xml format>>
	 * <billing>
	 * 	<account>
	 * 		<account-no></account-no>
	 * 		<security-no></security-no>
	 * 		<expire-date></expire-date>
	 * 		<account-type></account-type>
	 * 	</account>
	 *
	 * 	<billing-amount></billing-amount>
	 * 	<create-time></create-time>
	 * </billing>
	 * @param xmlStr
	 * @return
	 */
	String billingReqXmlMarshal(BillingDto billingDto){
		log.info "billingReqXmlMarshal start:{} $billingDto"

		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)

		builder.'billing'(){
			account() {
				'account-no' "${billingDto.accountNo}"
				'security-no' "${billingDto.securityNo}"
				'expire-date' "${billingDto.expireDate}"
				'account-type' "${billingDto.paymode}"
			}

			'billing-amount' "${billingDto.billingAmount}"
			'create-time' "${billingDto.billingTime}"
		}

		xmlStr = sw.toString()
		log.info "billingReqXmlMarshal end:{} $xmlStr"
		return xmlStr
	}

	/**
	 * <account-transaction>
	 * 	<transaction-no></transaction-no>
	 * 	<account-no></account-no>
	 * 	<code></code>
	 * 	<reasons></reasons>
	 *  <create-time></create-time>
	 * </account-transaction>
	 * @param accountTransactionResp
	 * @return
	 */
	BillingDto billingRespXmalUnmarshal(String xmlStr){
		log.info "billingRespXmalUnmarshal start:{} $xmlStr"
		def accountEle = new XmlSlurper().parseText(xmlStr)
		BillingDto billingTrans = new BillingDto(billingCode:accountEle."code".text(), billingMessage:accountEle."reasons".text())

		log.info "billingRespXmalUnmarshal end:{} $billingTrans"
		return billingTrans
	}
}
