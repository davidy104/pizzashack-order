package co.nz.pizzashack.billing.test

import groovy.util.logging.Slf4j

import javax.annotation.Resource

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import co.nz.pizzashack.billing.config.ApplicationConfiguration
import co.nz.pizzashack.billing.data.dto.AccountTransactionRespDto
import co.nz.pizzashack.billing.integration.mapping.BillingFormatTransformer

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = [
	ApplicationConfiguration.class
])
//@Ignore
class BillingXmlMappingTest {

	@Resource
	BillingFormatTransformer billingFormatTransformer

	@Test
	void testBillingRespXmlMarshal(){
		AccountTransactionRespDto resp = new AccountTransactionRespDto(transactionNo:'1234',
		accountNo:'111111',code:'002',reasons:'invalid accountno',createTime:'2013-10-10 23:12:42')
		String xmlStr = billingFormatTransformer.respXmlMarshal(resp)
		log.info "xmlStr:{} $xmlStr"
	}
}
