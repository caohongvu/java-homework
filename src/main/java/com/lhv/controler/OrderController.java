package com.lhv.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhv.configuration.DataFileConfig;
import com.lhv.data.helper.CSVWriteHelper;
import com.lhv.dto.OrderDto;
import com.lhv.dto.ResultObject;
import com.lhv.service.OrderService;
import com.lhv.utils.MessageConstants;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;
	
	@Autowired
	DataFileConfig dataFileConfig;

	@GetMapping(value = "/")
	public ResultObject<List<OrderDto>> getAll() {

		ResultObject<List<OrderDto>> resultObject = new ResultObject<>();
		try {
			resultObject.setData(orderService.getAll());
			resultObject.setStatus(HttpServletResponse.SC_OK);
			resultObject.setDescription(MessageConstants.MESSAGE_SUCCESS);
		} catch (IOException | ReflectiveOperationException e) {
			resultObject.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resultObject.setDescription(e.getMessage());
			logger.error(e);
		}
		return resultObject;
	}

	@GetMapping(value = "/vendor/{vendor}/")
	public ResultObject<List<OrderDto>> getAllByVendor(@PathVariable(name = "vendor", value = "") String vendor) {

		ResultObject<List<OrderDto>> resultObject = new ResultObject<>();
		try {
			
			List<OrderDto> orders =  orderService.getAllByVendor(vendor.toUpperCase());
			resultObject.setData(orders);
			
			List<Object[]> records = new ArrayList<>();
			String[] csvHeader = {"order_id", "item", "quantity"};
			String filePath = dataFileConfig.getDataFolder() +vendor.toUpperCase()+"_orders.csv";
			//Prepare data records
			orders.forEach(order -> {
				records.add(new Object[] {order.getId(), order.getItem(), order.getQuantity()});
			});
			//write file then return the path to user
			CSVWriteHelper.write(filePath, records, csvHeader);
		
			resultObject.setStatus(HttpServletResponse.SC_OK);
			resultObject.setDescription(filePath);
		} catch (IOException | ReflectiveOperationException e) {
			resultObject.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resultObject.setDescription(e.getMessage());
			logger.error(e);
		}
		return resultObject;
	}

}
