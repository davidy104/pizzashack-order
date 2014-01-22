package co.nz.pizzashack.billing.integration.mapping

import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder

import org.springframework.stereotype.Component

import co.nz.pizzashack.billing.data.dto.AccountDto
import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto
import co.nz.pizzashack.billing.data.dto.BillingTransactionDto
import co.nz.pizzashack.billing.utils.GeneralUtils

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
	BillingTransactionDto billingReqXmlUnmarshal(String xmlStr){
		log.info "billingReqXmlUnmarshal start:{} $xmlStr"
		def billingEle = new XmlSlurper().parseText(xmlStr)

		def accountEle = billingEle.account
		BillingTransactionDto billingTrans = new BillingTransactionDto()
		AccountDto account = new AccountDto(paymode:accountEle."account-type".text(),accountNo:accountEle."account-no".text(),securityNo:accountEle."security-no".text()
		,expireDate:accountEle."expire-date".text())
		log.info "account:{} $account"
		billingTrans.account = account

		billingTrans.billingAmount = new BigDecimal(billingEle."billing-amount".text())
		billingTrans.createTime = billingEle."create-time".text()

		log.info "billingReqXmlUnmarshal end:{} $billingTrans"
		return billingTrans
	}

	/**
	 * <account>
	 * 		<account-no></account-no>
	 * 		<security-no></security-no>
	 * 		<expire-date></expire-date>
	 * 		<account-type></account-type>
	 * 	</account>
	 * @param xmlStr
	 * @return
	 */
	AccountDto accountReqXmlUnmarshal(String xmlStr){
		log.info "accountReqXmlUnmarshal start:{} $xmlStr"
		def accountEle = new XmlSlurper().parseText(xmlStr)
		AccountDto account = new AccountDto(paymode:accountEle."account-type".text()
		,accountNo:accountEle."account-no".text()
		,securityNo:accountEle."security-no".text()
		,expireDate:accountEle."expire-date".text())

		log.info "accountReqXmlUnmarshal end:{} $account"
		return account
	}


	/**
	 * <account-transaction>
	 * 	<transaction-no></transaction-no>
	 * 	<account-no></account-no>
	 * 	<code></code>
	 * 	<reasons></reasons>
	 * <create-time></create-time>
	 * </account-transaction>
	 * @param accountTransactionResp
	 * @return
	 */
	String respXmlMarshal(AccountTransactionRespDto accountTransactionResp){
		log.info "respXmlMarshal start:{} $accountTransactionResp"

		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)

		builder.'account-transaction'() {
			'transaction-no' "${accountTransactionResp.transactionNo}"
			'account-no' "${accountTransactionResp.accountNo}"
			'code' "${accountTransactionResp.code}"
			'reasons' "${accountTransactionResp.reasons}"
			'create-time' "${accountTransactionResp.createTime}"
		}

		xmlStr = sw.toString()
		log.info "respXmlMarshal end:{} $xmlStr"
		return xmlStr
	}

	/**
	 *
	 * build response for duplicated request, data from T_BILLING_REQUEST_HISTORY
	 *
	 *
	 * <account-transaction>
	 * <transaction-no></transaction-no>
	 * 	<account-no></account-no>
	 * 	<code></code>
	 * 	<reasons></reasons>
	 * 	<create-time></create-time>
	 * </account-transaction>
	 * @param transMetaData
	 * @return
	 */
	String respXmlMarshalFromAccountTransQueryMap(Map transMetaData){
		log.info "respXmlMarshalFromAccountTransQueryMap start:{} $transMetaData"
		String xmlStr
		StringWriter sw = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(sw)

		String createTime = GeneralUtils.dateToStr(transMetaData['created_at'])

		builder.'account-transaction'() {
			'transaction-no' "${transMetaData['TRANS_NO']}"
			'account-no' "${transMetaData['ACCOUNT_NO']}"
			'code' "${transMetaData['CODE']}"
			'reasons' "${transMetaData['reasons']}"
			'create-time' "${createTime}"
		}

		xmlStr = sw.toString()
		log.info "respXmlMarshalFromAccountTransQueryMap end:{} $xmlStr"
		return xmlStr
	}
}
