package com.foodordering.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	void test() {
//		when(orderDetailRep.findByUserId(Mockito.anyInt(),Mockito.any())).thenReturn(page);
		StoreResponseDTO prRe1 = new StoreResponseDTO("Store details fetch success", 200);
		
		Store st = new Store();
		st.setStoreId(1);

		List<Store> storeList = new ArrayList<>();
		storeList.add(st);

		when(storeRepo.findAll()).thenReturn(storeList);
		
		StoreResponseDTO res=storeServiceImpl.getAllStoreDetails();
		assertEquals(prRe1.getMessage(), res.getMessage());
	}

}
