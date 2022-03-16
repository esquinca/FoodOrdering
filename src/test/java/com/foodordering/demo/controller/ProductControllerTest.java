package com.foodordering.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.foodordering.demo.dto.ProductRequestDTO;
import com.foodordering.demo.dto.ProductResponseDTO;
import com.foodordering.demo.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    
    private ObjectMapper objectMapper;
    private ProductRequestDTO productRequestDto;
    
    @BeforeEach
    void setUp() {

    	objectMapper = new ObjectMapper();
    	productRequestDto = new ProductRequestDTO();
    	productRequestDto.setAvailable(true);
    	productRequestDto.setProductCategory("VEG");
    	productRequestDto.setProductDescription("HEALTHY FOOD");
    	productRequestDto.setProductName("BEANS");
    	productRequestDto.setProductPrice(10.0);
    	productRequestDto.setStoreId(1);
    	
    	
    	
    }

	@Test
	void test() throws JsonProcessingException, Exception {

		  // When
		mockMvc.perform(post("/products")
				.content(objectMapper.writeValueAsString(productRequestDto))
                .contentType(MediaType.APPLICATION_JSON))
        
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("product saved successfuly"))
       ;
		

	}
	
	
	@Test
	void getProductsByStore() throws JsonProcessingException, Exception {

    	

    	ProductResponseDTO prRe= new ProductResponseDTO("success", 200);

		
    	when(productService.getProductDetailsByStoreId(Mockito.anyInt())).thenReturn(prRe);
    	
		  // When
		mockMvc.perform(get("/stores/1/products")
                .contentType(MediaType.APPLICATION_JSON)

        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.message").value("success"))
        .andExpect(jsonPath("$.statusCode").value(200))


        ;
		
//		verify(orderDetailService).orderdetails(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		

	}
}
