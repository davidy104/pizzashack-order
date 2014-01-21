package co.nz.pizzashack.billing.integration.route;

import javax.annotation.Resource

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

import co.nz.pizzashack.billing.ds.AccountDS
import co.nz.pizzashack.billing.integration.validation.AccountServicesValidator
import co.nz.pizzashack.billing.integration.ws.FaultHandler


@Component("accountServicesRoute")
class AccountServicesRoute extends RouteBuilder{

	static final String ENDPOINT = "cxf:bean:accountWsEndpoint"

	@Resource
	AccountDS accountDs

	@Resource
	AccountServicesValidator accountServicesValidator

	@Override
	void configure() {
		from(ENDPOINT).routeId(ENDPOINT)
				.onException(Exception.class)
				.handled(true)
				.setFaultBody(method(FaultHandler.class, "createFault"))
				.end()
				.to("log:input")
				.choice()
				.when(header('operationName').isEqualTo('accountAuthentication'))
				.bean(accountServicesValidator,"accountAuthenticationValidation")
				.bean(accountDs, "accountAuthentication")
				.endChoice()
				.when(header('operationName').isEqualTo('getAllTransactionsForAccount'))
				.bean(accountServicesValidator,"checkAccountTransactionsValidation")
				.bean(accountDs, "getAllTransactionsForAccount")
				.endChoice()
				.otherwise()
				.throwException(
				new Exception("opeartion can not be identified"))
				.end()
				.to("log:output")
	}
}
