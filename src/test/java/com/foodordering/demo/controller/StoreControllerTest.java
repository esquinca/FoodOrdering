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
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.service.StoreService;
@WebMvcTest(StoreController.class)
class StoreControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;
    
    Store store;
    StoreResponseDTO storeResponseDto;
    @BeforeEach
    void setUp() {
//    	store = new Store();
//		store.setStoreId(1);
//		store.setStoreName("something");
//		store.setStoreDescription("something else");
//		store.setRating(5);
    	storeResponseDto = new StoreResponseDTO("success", 200);
  
    }


	
	
	@Test
	void getAllStoreDetails() throws JsonProcessingException, Exception {

		

		
    	when(storeService.getAllStoreDetails()).thenReturn(storeResponseDto);
    	
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
