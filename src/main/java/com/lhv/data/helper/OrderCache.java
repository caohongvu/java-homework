package com.lhv.data.helper;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lhv.dto.OrderDto;

@Component
public class OrderCache extends HashMap<Object, List<OrderDto>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
