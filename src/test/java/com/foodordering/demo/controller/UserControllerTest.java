package com.foodordering.demo.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodordering.demo.dto.UserDto;
import com.foodordering.demo.dto.order.detail.OrderDetailDto;
import com.foodordering.demo.dto.order.detail.OrderDetailResponse;
import com.foodordering.demo.dto.order.detail.ProductDetail;
import com.foodordering.demo.dto.request.UserReq;
import com.foodordering.demo.service.IUserService;
import com.foodordering.demo.service.OrderDetailService;
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService iUserService;
    
    @MockBean
	private OrderDetailService orderDetailService;
    
    ObjectMapper	objectMapper;
    
    @BeforeEach
    void setUp() {
   
    	
    	objectMapper = new ObjectMapper();
    }
	
	@Test
	void users() throws JsonProcessingException, Exception {
	 	UserReq userRe = new UserReq();
    	userRe.setName("osva");
    	userRe.setPassword("123567salsa");
    	
		UserDto us=new UserDto();
		us.setUserId(1);
		us.setMessage("Successful Login");
		us.setStatusCode("200 OK");
		
    	when(iUserService.findByNameAndPassword(userRe.getName(), userRe.getPassword())).thenReturn(us);
    	
		  // When
		mockMvc.perform(post("/users/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRe)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.userId").value(1))
        .andExpect(jsonPath("$.message").value("Successful Login"))
        .andExpect(jsonPath("$.statusCode").value("200 OK"));
		
		verify(iUserService).findByNameAndPassword(userRe.getName(), userRe.getPassword());
		

	}
	
	
	@Test
	void usersValidationFail() throws JsonProcessingException, Exception {
	 	UserReq userRe = new UserReq();
    	userRe.setName("");
    	userRe.setPassword("123567mambo");
    	
	
 	
		  // When
		mockMvc.perform(post("/users/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRe)))
        .andExpect(status().is2xxSuccessful())
        
        .andExpect(jsonPath("$.message").value("invalid arguments parameters"))
        .andExpect(jsonPath("$.invalidArguments.name").value("product name should not be empty"));
		
		
		

	}
	
	@Test
	void usersValidationFail2() throws JsonProcessingException, Exception {
	 	UserReq userRe = new UserReq();
    	userRe.setName("osva");
    	userRe.setPassword("2");
    	

		
 	
		  // When
		mockMvc.perform(post("/users/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userRe)))
        .andExpect(status().is2xxSuccessful())
        
        .andExpect(jsonPath("$.message").value("invalid arguments parameters"))
        .andExpect(jsonPath("$.invalidArguments.password").value("password must be greater than 1"));
		
		
		

	}
	
	
	@Test
	void orderDetails() throws JsonProcessingException, Exception {

    	

    	OrderDetailResponse orderDetDto=new OrderDetailResponse();

		orderDetDto.setStoreId("1");
		orderDetDto.setStoreName("ferre");
		orderDetDto.setTotalPrice(1.2);
		
		ProductDetail prDet= new ProductDetail();
		prDet.setProductName("screwdriver");
		prDet.setQuantity("3");
		orderDetDto.getProductDetails().add(prDet);
		
		List<OrderDetailResponse> order = new ArrayList<OrderDetailResponse>();
		order.add(orderDetDto);
		
		OrderDetailDto orderDto=	new OrderDetailDto();
		orderDto.setData(order);
		
    	when(orderDetailService.orderdetails(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(orderDto);
    	
		  // When
		mockMvc.perform(get("/users/v1/users/1/orderdetails")
                .contentType(MediaType.APPLICATION_JSON)

        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.data[0].storeName").value(orderDto.getData().get(0).getStoreName()))
        .andExpect(jsonPath("$.data[0].storeId").value(orderDto.getData().get(0).getStoreId()))
        .andExpect(jsonPath("$.data[0].totalPrice").value(orderDto.getData().get(0).getTotalPrice()))
        .andExpect(jsonPath("$.data[0].productDetails[0].productName").value(orderDto.getData().get(0).getProductDetails().get(0).getProductName()))

        ;
		
//		verify(orderDetailService).orderdetails(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		

	}

}
