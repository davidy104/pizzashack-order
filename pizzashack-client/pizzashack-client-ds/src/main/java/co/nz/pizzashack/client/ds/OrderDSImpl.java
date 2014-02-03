package co.nz.pizzashack.client.ds;

import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse.Status;

import co.nz.pizzashack.client.data.dto.BillingDto;
import co.nz.pizzashack.client.data.dto.BillingResp;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.integration.route.OrderProcessRoute;
import co.nz.pizzashack.client.integration.ws.client.stub.BillingResponse;
import co.nz.pizzashack.client.utils.GeneralUtils;
import co.nz.pizzashack.client.utils.JerseyClientSupport;
import co.nz.pizzashack.client.utils.PizzashackJSONTransformer;

import static co.nz.pizzashack.client.ds.PizzashackDSImpl.BASE_URL;

@Service
public class OrderDSImpl extends JerseyClientSupport implements OrderDS {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderDSImpl.class);

	@Produce
	private ProducerTemplate producer;

	@Override
	public OrderDto placeOrder(OrderDto order) throws Exception {
		LOGGER.info("placeOrder start:{} ", order);
		String requestId = String.valueOf(GeneralUtils.getRandomNumber(5));
		OrderDto orderResponse = producer.requestBodyAndHeader(
				OrderProcessRoute.ORDER_INTEGRATION_ENDPOINT, order,
				"messageId", requestId, OrderDto.class);

		LOGGER.info("placeOrder end:{} ", orderResponse);
		return orderResponse;
	}

	@Override
	public OrderDto getOrderByNo(String orderNo) throws Exception {
		OrderDto orderDto = null;
		WebResource webResource = client.resource(BASE_URL).path(
				"order/" + orderNo);

		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = response.getClientResponseStatus();
		LOGGER.info("statusCode:{} ", statusCode);
		String respStr = this.getResponsePayload(response);
		if (statusCode.equals(Status.OK)) {
			orderDto = PizzashackJSONTransformer.getOrder(respStr);
		} else {
			throw new Exception("getOrderByNo error: " + respStr);
		}
		return orderDto;
	}

	@Override
	public Set<OrderDto> getOrdersByCustomer(String customerEmail)
			throws Exception {
		LOGGER.info("getOrdersByCustomer start:{} ", customerEmail);
		Set<OrderDto> orders = null;
		WebResource webResource = client.resource(BASE_URL).path(
				"customerOrderList/" + customerEmail);
		ClientResponse response = webResource
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Status statusCode = response.getClientResponseStatus();
		LOGGER.info("statusCode:{} ", statusCode);
		String respStr = this.getResponsePayload(response);
		if (statusCode.equals(Status.OK)) {
			orders = PizzashackJSONTransformer.getOrders(respStr);
		} else {
			throw new Exception("getOrdersByCustomer error: " + respStr);
		}
		LOGGER.info("getOrdersByCustomer end:{} ");
		return orders;
	}

	@Override
	public BillingResp payForOrder(BillingDto billing) throws Exception {
		LOGGER.info("payForOrder start:{} ", billing);
		BillingResp billingResp = null;
		BillingResponse billingResponse = producer.requestBody(
				"cxf:bean:billingProcessEndpoint?dataFormat=POJO", billing,
				BillingResponse.class);
		billingResp = new BillingResp();
		billingResp.setBillingCode(billingResponse.getBillingCode());
		billingResp.setBillingMessage(billingResponse.getBillingMessage());
		billingResp.setOrderNo(billingResponse.getOrderNo());
		LOGGER.info("payForOrder end:{} ", billingResp);
		return billingResp;
	}

}
