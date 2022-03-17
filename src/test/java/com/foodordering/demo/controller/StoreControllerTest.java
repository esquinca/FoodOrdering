package com.foodordering.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foodordering.demo.dto.StoreResponseDTO;
import com.foodordering.demo.service.StoreService;
@WebMvcTest(StoreController.class)
class StoreControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;
    
 
    
    @BeforeEach
    void setUp() {


    	
    }


	
	
	@Test
	void getAllStoreDetails() throws JsonProcessingException, Exception {

    	

		StoreResponseDTO prRe= new StoreResponseDTO("success", 200);

		
    	when(storeService.getAllStoreDetails()).thenReturn(prRe);
    	
		  // When
		mockMvc.perform(get("/stores")
                .contentType(MediaType.APPLICATION_JSON)

        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.message").value("success"))
        .andExpect(jsonPath("$.statusCode").value(200))


        ;
		
		

	}
}
