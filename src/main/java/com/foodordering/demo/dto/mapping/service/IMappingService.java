package com.foodordering.demo.dto.mapping.service;

import java.util.List;

import com.foodordering.demo.dto.UserDto;
import com.foodordering.demo.dto.order.detail.OrderDetailDto;
import com.foodordering.demo.entity.OrderDetail;
import com.foodordering.demo.entity.User;

public interface IMappingService {
	
	UserDto mappingUSer(User user);
	
	OrderDetailDto mappingOrderDetail(List<OrderDetail> ordersDetail);

}
