package com.foodordering.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import com.foodordering.demo.dto.OrderRequestDTO;
import com.foodordering.demo.dto.OrderResponseDTO;
import com.foodordering.demo.dto.ProductListOrderDetailDTO;
import com.foodordering.demo.service.OrderDetailService;


@WebMvcTest(OrderDetailController.class)
class OrderDetailControllerTest {

	   @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private OrderDetailService orderService;
	    
	    private ObjectMapper objectMapper;
	    private OrderRequestDTO orderRequestDto;
	    private ProductListOrderDetailDTO prodListODDto;
	    
	    @BeforeEach
	    void setUp() {
	    	objectMapper = new ObjectMapper();
	    	
	    	
	    	prodListODDto = new ProductListOrderDetailDTO();
	    	prodListODDto.setProductId(1);
	    	prodListODDto.setProductPrice(100.00);
	    	prodListODDto.setQuantity(1);
	    	
	    	orderRequestDto = new OrderRequestDTO();
	    	orderRequestDto.setUserId(1);
	    	orderRequestDto.setStoreId(1);
	    	orderRequestDto.setTotalPrice(4.0);
	    	orderRequestDto.setOrderNumber("1");
	    	orderRequestDto.setProductList(List.of(prodListODDto));
	    	
	    }

		@Test
		void test() throws JsonProcessingException, Exception {
            when(orderService.saveOrderDetails(Mockito.any())).thenReturn(new OrderResponseDTO("successfull", 200));
			  // When
			mockMvc.perform(post("/orderDetails")
					.content(objectMapper.writeValueAsString(orderRequestDto))
	                .contentType(MediaType.APPLICATION_JSON))
	        
	        .andExpect(status().is2xxSuccessful())
	        .andExpect(jsonPath("$.statusCode").value(200))
	        .andExpect(jsonPath("$.message").value("successfull"))
	       ;
			

		}
}
