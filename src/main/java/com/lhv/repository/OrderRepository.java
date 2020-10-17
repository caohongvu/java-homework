package com.lhv.repository;

import java.io.IOException;
import java.util.List;

import com.lhv.dto.OrderDto;

public interface OrderRepository {

	List<OrderDto> findAll() throws IOException, ReflectiveOperationException;

	List<OrderDto> findAllByVendor(String vendor);

}
