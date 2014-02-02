package co.nz.pizzashack.controller;

import static co.nz.pizzashack.controller.OrderController.MODEL_ATTRIBUTE_ORDERPROCESS;
import static co.nz.pizzashack.controller.OrderController.MODEL_ATTRIBUTE_ORDERPROCESSES;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.nz.pizzashack.data.dto.OrderProcessDto;
import co.nz.pizzashack.data.dto.ProcessActivityDto;
import co.nz.pizzashack.ds.OrderProcessQueryDS;

@Controller
@RequestMapping("/orderQuery")
public class OrderQueryController extends BaseController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderQueryController.class);

	@Resource
	private OrderProcessQueryDS orderProcessQueryDs;

	public static final String ORDER_FOLDER = "order/";

	public static final String ORDERPROCESS_INDEX_VIEW = ORDER_FOLDER + "index";
	public static final String ORDERPROCESS_SHOW_VIEW = ORDER_FOLDER + "show";

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) throws Exception {
		LOGGER.info("userIndex start: {}");
		Set<OrderProcessDto> orderProcesses = orderProcessQueryDs
				.getAllOrderProcesses();

		model.addAttribute(MODEL_ATTRIBUTE_ORDERPROCESSES, orderProcesses);
		return ORDERPROCESS_INDEX_VIEW;
	}

	@RequestMapping(value = "/{orderNo}", method = RequestMethod.GET)
	public String show(@PathVariable("orderNo") String orderNo, Model model)
			throws Exception {
		LOGGER.info("show start: {}", orderNo);
		OrderProcessDto found = orderProcessQueryDs
				.getOrderProcessByOrderNo(orderNo);

		LOGGER.info("Found user: {}", found);
		model.addAttribute(MODEL_ATTRIBUTE_ORDERPROCESS, found);
		return ORDERPROCESS_SHOW_VIEW;
	}

	@RequestMapping(value = "/showHistory/{orderNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Set<ProcessActivityDto> showHistory(
			@PathVariable("orderNo") String orderNo) throws Exception {
		LOGGER.info("showHistory start: {}");
		Set<ProcessActivityDto> histories = orderProcessQueryDs
				.getHistoryProcessActivitiesByOrderNo(orderNo);
		return histories;
	}

}
