package com.lhv.service;

import java.io.IOException;
import java.util.List;

import com.lhv.dto.OrderDto;


public interface OrderService {

	
	List<OrderDto> getAll() throws IOException, ReflectiveOperationException;
	List<OrderDto> getAllByVendor(String vendor) throws IOException, ReflectiveOperationException;
	
}
