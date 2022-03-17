package com.foodordering.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foodordering.demo.dto.ProductRequestDTO;
import com.foodordering.demo.entity.Address;
import com.foodordering.demo.entity.Product;
import com.foodordering.demo.entity.ProductCategory;
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.exception.StoreNotFoundException;
import com.foodordering.demo.repo.ProductRepo;
import com.foodordering.demo.repo.StoreRepo;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
	@Mock
	StoreRepo storeRepo; //mocks objects
	@Mock
	ProductRepo productRepo;
	
	@InjectMocks
	ProductServiceImpl productServiceImpl; // real object with mock dependency objects
	
	ProductRequestDTO productRequestDTO;
	Store store;
	
	@BeforeEach
	public void setUp() {
		productRequestDTO = new ProductRequestDTO();
		productRequestDTO.setProductName("Veg pizza");
		productRequestDTO.setProductCategory("VEG");
		productRequestDTO.setAvailable(true);
		productRequestDTO.setProductDescription("Veg cheese pizza");
		productRequestDTO.setProductPrice(500.00);
		productRequestDTO.setStoreId(1);
		
		Product product = new Product();
		product.setProductId(1);
		product.setProductCategory(ProductCategory.VEG);
		product.setProductDescription("Veg Sandwich");
		product.setProductName("Sandwich");
		product.setAvailable(true);
		product.setProductPrice(100.00);
		product.setStore(store);
		
		Address address = new Address();
		address.setCity("Cancun");
		address.setPincode("77500");
		address.setStreet("huayacan street");
		
		
		store = new Store();
		store.setStoreId(1);
		store.setProductList(List.of(product));
		store.setRating(4);
		store.setStoreAddress(address);
		store.setStoreName("Hari Sandwich");
		store.setStoreDescription("sandwiches, pizza, refreshments...");
	}
	
	@Test
	@DisplayName("Save product detail: positive")
	public void saveProductDetailsTest() {
		//Identify other layer calls example... repos function etc...
		//Stub other layers
		
		//stub storeRepo.findbyid
		when(storeRepo.findById(1)).thenReturn(Optional.of(store));
		
		
		//stub productRepo.save
		when(productRepo.save(any(Product.class))).thenAnswer(i -> {
			Product product = i.getArgument(0);
			product.setProductId(2);
			return product;
		});
		
		Product productResult = productServiceImpl.saveProductDetails(productRequestDTO);
		assertNotNull(productResult);
		assertEquals(1, productResult.getStore().getStoreId());
		assertEquals("Veg pizza", productResult.getProductName());
	}
	
	@Test
	@DisplayName("Save product detail: negative")
	public void saveProductDetailsTest1() {
		//Identify other layer calls example... repos function etc...
		//Stub other layers
		
		//stub storeRepo.findbyid
		when(storeRepo.findById(1)).thenReturn(Optional.empty());
		
		
		//stub productRepo.save
//		when(productRepo.save(any(Product.class))).thenAnswer(i -> {
//			Product product = i.getArgument(0);
//			product.setProductId(2);
//			return product;
//		});
		
		
		assertThrows(StoreNotFoundException.class, () -> productServiceImpl.saveProductDetails(productRequestDTO));
	}
	
}
