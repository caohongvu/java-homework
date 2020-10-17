package com.lhv.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lhv.configuration.DataFileConfig;
import com.lhv.data.helper.CSVDataParser;
import com.lhv.data.helper.CSVParserHelper;
import com.lhv.data.helper.OrderCache;
import com.lhv.dto.OrderDto;
import com.lhv.repository.OrderRepository;

@Repository("CSVOrderRepository")
public class OrderRepositoryCSVImpl implements OrderRepository {

	private static final Logger logger = LogManager.getLogger(OrderRepositoryCSVImpl.class);

	@Autowired
	CSVDataParser<OrderDto> parser;

	@Autowired
	DataFileConfig dataFileConfig;

	CSVParserHelper orderHelper;

	@Autowired
	OrderCache orderCache;

	@PostConstruct
	void init() throws IOException, ReflectiveOperationException {
		orderHelper = new CSVParserHelper(parser);

		// read and load all data in csv file then put it into the memory cache
		List<OrderDto> allOrders = findAll();
		loadAllDataToCache(allOrders);
		
	}
	
	@Override
	public List<OrderDto> findAll() throws IOException, ReflectiveOperationException {
		List<OrderDto> allOrders = new ArrayList<>();
		try (Stream<Path> paths = Files.walk(Paths.get(dataFileConfig.getDataFolder()))) {
			paths.filter(Files::isRegularFile).forEach(path -> {
				try {
					if(path.toString().endsWith(".csv") && !path.toString().contains("_orders")) {
						allOrders.addAll(orderHelper.parse(path.toString()));
					}
				} catch (IOException | ReflectiveOperationException e) {
					logger.error(e);
				}
			});
		}
		
		logger.info("Reload data to memory cache!");
		loadAllDataToCache(allOrders);
		
		return allOrders;
	}

	@Override
	public List<OrderDto> findAllByVendor(String vendor) {
		return orderCache.get(vendor) != null ? orderCache.get(vendor.toUpperCase()) : new ArrayList<>();
	}
	
	private void loadAllDataToCache(List<OrderDto> allOrders) {
		orderCache.clear();
		
		allOrders.forEach(order -> {
			List<OrderDto> orders = orderCache.get(order.getVendor());
			if (orders == null) {
				orders = new ArrayList<>();
			}
			orders.add(order);
			orderCache.put(order.getVendor().toUpperCase(), orders);
		});
		logger.info("Put all data to memory Cache already!");
	}

}
