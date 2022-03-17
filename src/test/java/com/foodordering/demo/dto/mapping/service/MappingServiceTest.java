package com.foodordering.demo.dto.mapping.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foodordering.demo.dto.UserDto;
import com.foodordering.demo.dto.order.detail.OrderDetailDto;
import com.foodordering.demo.entity.OrderDetail;
import com.foodordering.demo.entity.OrderProduct;
import com.foodordering.demo.entity.OrderStatus;
import com.foodordering.demo.entity.Product;
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.entity.User;
import com.foodordering.demo.exception.ProductNotFoundException;
import com.foodordering.demo.repo.ProductRepo;
@ExtendWith(MockitoExtension.class)
class MappingServiceTest {
	

	 
	@Mock
	private ProductRepo productRepo; 
	@InjectMocks
	private MappingService mappingService;

	
	
	@Test
	@DisplayName("mapping user")
	void mappingUSer() {

    	
		UserDto us=new UserDto();
		us.setUserId(1);
		us.setMessage("Successful Login");
		us.setStatusCode("200 OK");
		
		User user = new User();
		user.setEmail("osva@12.com");
		user.setUserId(1);

    	

    	UserDto useDto=mappingService.mappingUSer(user);
    	assertAll(()->assertNotNull(useDto.getMessage()),
    			()->assertNotNull(useDto.getStatusCode()),
    			()->assertNotNull(useDto.getUserId())
    			);
    	
    	assertAll(()->assertEquals("Successful Login",useDto.getMessage()),
    			()->assertEquals("200 OK",useDto.getStatusCode()),
    			()->assertEquals(1,useDto.getUserId())
    			);
	
	}
	
	
	@Test
	void mappingOrderDetail() throws JsonProcessingException, Exception {

    	
		//prarameter that receive method
		
		List<OrderProduct> orderPro = new ArrayList<>();
		OrderProduct orp= new OrderProduct();
		orp.setProductId(1);
		orp.setProductPrice(10);
		orp.setQuantity(2);
		
		orderPro.add(orp);
		
		
		List<OrderDetail> ordersDetail = new ArrayList<>();
		OrderDetail ord1= new OrderDetail();
		ord1.setOrderDate(null);
		ord1.setOrderDetailId(1);
		ord1.setOrderNumber("1");
		ord1.setOrderProduct(orderPro);
		ord1.setStatus(OrderStatus.DELIVERED);
		ord1.setStore(new Store());
		ord1.setTotalPrice(1);
		ord1.setUserId(1);
		
		ordersDetail.add(ord1);
		
		Product product = new Product();
		product.setProductName("banana");
		product.setProductPrice(23.4);
		
		
		when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
    	
		OrderDetailDto  response = mappingService.mappingOrderDetail(ordersDetail);
		
		verify(productRepo).findById(Mockito.anyInt());
		
		assertAll(
    			()->assertEquals(null,response.getData().get(0).getStoreName()),
    			()->assertEquals(1.0,response.getData().get(0).getTotalPrice())
    			);
		

	}
	
	
	
	@Test
	void mappingOrderDetailFail() throws JsonProcessingException, Exception {

    	
		//prarameter that receive method
		
		List<OrderProduct> orderPro = new ArrayList<>();
		OrderProduct orp= new OrderProduct();
		orp.setProductId(1);
		orp.setProductPrice(10);
		orp.setQuantity(2);
		
		orderPro.add(orp);
		
		
		List<OrderDetail> ordersDetail = new ArrayList<>();
		OrderDetail ord1= new OrderDetail();
		ord1.setOrderDate(null);
		ord1.setOrderDetailId(1);
		ord1.setOrderNumber("1");
		ord1.setOrderProduct(orderPro);
		ord1.setStatus(OrderStatus.DELIVERED);
		ord1.setStore(new Store());
		ord1.setTotalPrice(1);
		ord1.setUserId(1);
		
		ordersDetail.add(ord1);
		
	
//		when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    	
		assertThrows(ProductNotFoundException.class,()-> mappingService.mappingOrderDetail(ordersDetail));

	}
	



}
