package co.nz.pizzashack.billing.integration.route;

import javax.annotation.Resource;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import co.nz.pizzashack.billing.ds.AccountDS;
import co.nz.pizzashack.billing.integration.validation.AccountServicesValidator;
import co.nz.pizzashack.billing.integration.ws.FaultHandler;

@Component("accountServicesRoute")
public class AccountServicesRoute extends RouteBuilder {

	public static final String ENDPOINT = "cxf:bean:accountWsEndpoint";

	@Resource
	private AccountDS accountDs;

	@Resource
	private AccountServicesValidator accountServicesValidator;

	@Override
	public void configure() throws Exception {
		from(ENDPOINT)
				.routeId(ENDPOINT)
				.onException(Exception.class)
				.handled(true)
				.setFaultBody(method(FaultHandler.class, "createFault"))
				.end()
				.to("log:input")
				.choice()
				.when(header("operationName")
						.isEqualTo("accountAuthentication"))
				.to("direct:authentication")
				.endChoice()
				.when(header("operationName").isEqualTo(
						"getAllTransactionsForAccount"))
				.to("direct:getAllTransactionsForAccount")
				.endChoice()
				.when(header("operationName").isEqualTo("billingProcess"))
				.to("direct:billingProcess")
				.endChoice()
				.otherwise()
				.throwException(
						new Exception("opeartion can not be identified")).end()
				.to("log:output");

		from("direct:authentication")
				.routeId("direct:authentication")
				.bean(accountServicesValidator,
						"accountAuthenticationValidation")
				.bean(accountDs, "accountAuthentication");

		from("direct:getAllTransactionsForAccount")
				.routeId("direct:getAllTransactionsForAccount")
				.bean(accountServicesValidator,
						"checkAccountTransactionsValidation")
				.bean(accountDs, "getAllTransactionsForAccount");

		from("direct:billingProcess").routeId("direct:billingProcess")
				.transacted("PROPAGATION_REQUIRED").bean(accountDs, "deduct");

	}
}
