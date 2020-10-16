package com.lhv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lhv.dto.OrderDto;
import com.lhv.service.OrderService;


@Service
public class OrderServiceImpl implements OrderService {


	@Override
	public List<OrderDto> getAll() {
		return new ArrayList<OrderDto>();
	}
	
}
