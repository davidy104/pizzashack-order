package co.nz.pizzashack.client.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import co.nz.pizzashack.client.data.dto.OrderDetailsDto;
import co.nz.pizzashack.client.data.dto.OrderDto;
import co.nz.pizzashack.client.data.dto.PizzashackDto;

public class PizzashackJSONTransformer {

	/**
	 * @param pizzaContents
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<PizzashackDto> getAvailablePizzaList(String pizzaContents)
			throws Exception {
		Set<PizzashackDto> pizzaList = null;
		if (!StringUtils.isEmpty(pizzaContents)) {
			JSONParser parser = new JSONParser();
			pizzaList = new HashSet<PizzashackDto>();
			Object obj = parser.parse(pizzaContents);
			JSONArray array = (JSONArray) obj;
			Iterator<JSONObject> iterator = array.iterator();
			PizzashackDto pizza = null;
			while (iterator.hasNext()) {
				pizza = new PizzashackDto();
				JSONObject pizzaItem = iterator.next();
				pizza.setPizzashackId((Long) pizzaItem.get("pizzashackId"));
				pizza.setPizzaName((String) pizzaItem.get("pizzaName"));
				pizza.setDescription((String) pizzaItem.get("description"));

				BigDecimal price = new BigDecimal(
						(Double) pizzaItem.get("price"));

				if (pizzaItem.get("afterDiscount") != null) {
					BigDecimal afterDiscount = new BigDecimal(
							(Double) pizzaItem.get("afterDiscount"));
					pizza.setAfterDiscount(afterDiscount);
				}

				pizza.setPrice(price);
				pizzaList.add(pizza);
			}

		}

		return pizzaList;
	}

	/**
	 * 
	 * @param orderJson
	 * @return
	 */
	public static PizzashackDto getPizzashack(String pizzashackJson)
			throws Exception {
		JSONParser parser = new JSONParser();

		PizzashackDto pizza = new PizzashackDto();

		Object obj = parser.parse(pizzashackJson);
		JSONObject pizzaItem = (JSONObject) obj;
		pizza.setPizzashackId((Long) pizzaItem.get("pizzashackId"));
		pizza.setPizzaName((String) pizzaItem.get("pizzaName"));
		pizza.setDescription((String) pizzaItem.get("description"));

		BigDecimal price = new BigDecimal((Double) pizzaItem.get("price"));

		if (pizzaItem.get("afterDiscount") != null) {
			BigDecimal afterDiscount = new BigDecimal(
					(Double) pizzaItem.get("afterDiscount"));
			pizza.setAfterDiscount(afterDiscount);
		}

		pizza.setPrice(price);
		return pizza;

	}

	@SuppressWarnings("unchecked")
	public static Set<OrderDto> getOrders(String ordersJson) throws Exception {
		Set<OrderDto> orders = null;
		JSONParser parser = new JSONParser();
		if (!StringUtils.isEmpty(ordersJson)) {
			orders = new HashSet<OrderDto>();
			Object obj = parser.parse(ordersJson);
			JSONArray array = (JSONArray) obj;
			Iterator<JSONObject> iterator = array.iterator();
			OrderDto order = null;
			while (iterator.hasNext()) {
				order = new OrderDto();
				JSONObject orderItem = iterator.next();
				order.setOrderId((Long) orderItem.get("orderId"));
				order.setOrderNo((String) orderItem.get("orderNo"));
				Long qtyLong = (Long) orderItem.get("qty");
				order.setQty(qtyLong.intValue());
				order.setAddress((String) orderItem.get("address"));
				order.setStatus((String) orderItem.get("status"));
				order.setTotalPrice(new BigDecimal((Double) orderItem
						.get("totalPrice")));
				order.setOrderTime((String) orderItem.get("orderTime"));
				order.setDeliverTime((String) orderItem.get("deliverTime"));
				orders.add(order);
			}
		}
		return orders;
	}

	@SuppressWarnings("unchecked")
	public static OrderDto getOrder(String orderJson) throws Exception {
		JSONParser parser = new JSONParser();
		OrderDto order = new OrderDto();
		Object obj = parser.parse(orderJson);
		JSONObject orderItem = (JSONObject) obj;
		order.setOrderId((Long) orderItem.get("orderId"));
		order.setOrderNo((String) orderItem.get("orderNo"));
		Long qtyLong = (Long) orderItem.get("qty");
		order.setQty(qtyLong.intValue());
		order.setAddress((String) orderItem.get("address"));
		order.setStatus((String) orderItem.get("status"));
		order.setTotalPrice(new BigDecimal((Double) orderItem.get("totalPrice")));
		order.setOrderTime((String) orderItem.get("orderTime"));
		order.setDeliverTime((String) orderItem.get("deliverTime"));

		if (orderItem.get("orderDetailsSet") != null) {
			JSONArray array = (JSONArray) orderItem.get("orderDetailsSet");
			Iterator<JSONObject> iterator = array.iterator();
			while (iterator.hasNext()) {
				OrderDetailsDto orderDetail = new OrderDetailsDto();
				JSONObject orderDetailItem = iterator.next();
				orderDetail.setPizzaName((String) orderDetailItem
						.get("pizzaName"));
				Long detailQty = (Long) orderItem.get("qty");
				orderDetail.setQty(detailQty.intValue());
				orderDetail.setTotalPrice(new BigDecimal((Double) orderItem
						.get("totalPrice")));
				order.addOrderDetails(orderDetail);
			}
		}

		return order;

	}

}
