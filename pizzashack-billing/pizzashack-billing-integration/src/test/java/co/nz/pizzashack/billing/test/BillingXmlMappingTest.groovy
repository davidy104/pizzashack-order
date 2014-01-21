package co.nz.pizzashack.billing.test

import groovy.util.logging.Slf4j

import org.junit.Test

import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto
import co.nz.pizzashack.billing.integration.mapping.BillingFormatTransformer

@Slf4j
class BillingXmlMappingTest {

	BillingFormatTransformer billingFormatTransformer =new BillingFormatTransformer()

	@Test
	void testBillingRespXmlMarshal(){
		AccountTransactionRespDto resp = new AccountTransactionRespDto(transactionNo:'1234',
			accountNo:'111111',code:'002',reasons:'invalid accountno',createTime:'2013-10-10 23:12:42')
		String xmlStr = billingFormatTransformer.respXmlMarshal(resp)
		log.info "xmlStr:{} $xmlStr"
	}
}
