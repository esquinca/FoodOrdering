package com.foodordering.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foodordering.demo.dto.StoreResponseDTO;
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.repo.ProductRepo;
import com.foodordering.demo.repo.StoreRepo;


@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

	@Mock
	private StoreRepo storeRepo;

	@InjectMocks
	private StoreServiceImpl storeServiceImpl;
	
	
	Store store;
	StoreResponseDTO storeResponseDto;
	
	@BeforeEach
	public void setUp() {
		store = new Store();
		store.setStoreId(1);
		
		
		storeResponseDto = new StoreResponseDTO("Store details fetch success", 200);
		//List<Store> storeList = new ArrayList<>();
		//storeList.add(st);
	}
	
	
	@Test
	void test() {
//		when(orderDetailRep.findByUserId(Mockito.anyInt(),Mockito.any())).thenReturn(page);
		
		
		//Store st = new Store();
		//st.setStoreId(1);

		//List<Store> storeList = new ArrayList<>();
		//storeList.add(st);

		//when(storeRepo.findAll()).thenReturn(storeList);
		
		when(storeRepo.findAll()).thenReturn(List.of(store));
		
		
		
		StoreResponseDTO res=storeServiceImpl.getAllStoreDetails();
		assertEquals(storeResponseDto.getMessage(), res.getMessage());
	}

}
