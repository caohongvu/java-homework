package com.lhv.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.lhv.dto.OrderDto;
import com.lhv.repository.OrderRepository;
import com.lhv.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {


	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

	@Autowired
	@Qualifier("CSVOrderRepository")
	OrderRepository orderRepository;

	
	@Override
	public List<OrderDto> getAll() throws IOException, ReflectiveOperationException {
		logger.info("Find all orders");
		return orderRepository.findAll();
	}

	@Override
	public List<OrderDto> getAllByVendor(String vendor) throws IOException, ReflectiveOperationException {
		logger.info("Find all orders by vendor {}", vendor);	
		return orderRepository.findAllByVendor(vendor);
	}
	
}
