package com.foodordering.demo.dto.mapping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodordering.demo.dto.UserDto;
import com.foodordering.demo.dto.order.detail.OrderDetailDto;
import com.foodordering.demo.dto.order.detail.OrderDetailResponse;
import com.foodordering.demo.dto.order.detail.ProductDetail;
import com.foodordering.demo.entity.OrderDetail;
import com.foodordering.demo.entity.Product;
import com.foodordering.demo.entity.User;
import com.foodordering.demo.exception.ProductNotFoundException;
import com.foodordering.demo.repo.ProductRepo;


@Service
public class MappingService implements IMappingService{
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public UserDto mappingUSer(User user) {
		//UserDto.builder().userId(user.getUserId()).message("Successful Login").statusCode("200 OK").build();
		UserDto us=new UserDto();
		us.setMessage("Successful Login");
		us.setStatusCode("200 OK");
		us.setUserId(user.getUserId());
		return us;
	}

	@Override
	public OrderDetailDto mappingOrderDetail(List<OrderDetail> ordersDetail) {
		List<OrderDetailResponse> order =	ordersDetail.stream().map(od->{
			OrderDetailResponse orderDetDto=new OrderDetailResponse();
			orderDetDto.setOrderDate(od.getOrderDate());
			orderDetDto.setStoreId(od.getStore().getStoreId()+"");
			orderDetDto.setStoreName(od.getStore().getStoreName());
			orderDetDto.setTotalPrice(od.getTotalPrice());
			System.out.println("list of products:"+od.getOrderProduct());
			
			od.getOrderProduct().forEach(pro->{
				Product product =productRepo.findById(pro.getProductId()).orElseThrow(()->new ProductNotFoundException("Product was not found"));
				ProductDetail prDet= new ProductDetail();
				prDet.setProductName(product.getProductName());
				prDet.setQuantity(pro.getProductPrice()+"");
				orderDetDto.getProductDetails().add(prDet);
				
			});
			return orderDetDto;
			
		}).collect(Collectors.toList());
		

		OrderDetailDto orderDto=	new OrderDetailDto();
		orderDto.setData(order);
		 
		return  orderDto;
	}

}
