package com.foodordering.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foodordering.demo.dto.OrderRequestDTO;
import com.foodordering.demo.dto.OrderResponseDTO;
import com.foodordering.demo.dto.ProductListOrderDetailDTO;
import com.foodordering.demo.dto.mapping.service.IMappingService;
import com.foodordering.demo.dto.order.detail.OrderDetailDto;
import com.foodordering.demo.dto.order.detail.OrderDetailResponse;
import com.foodordering.demo.dto.order.detail.ProductDetail;
import com.foodordering.demo.entity.OrderDetail;
import com.foodordering.demo.entity.OrderProduct;
import com.foodordering.demo.entity.OrderStatus;
import com.foodordering.demo.entity.Product;
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.exception.OrderDetailNotFoundException;
import com.foodordering.demo.exception.ProductNotFoundException;
import com.foodordering.demo.exception.StoreNotFoundException;
import com.foodordering.demo.repo.OrderDetailRepository;
import com.foodordering.demo.repo.ProductRepo;
import com.foodordering.demo.repo.StoreRepo;

@ExtendWith(MockitoExtension.class)
class OrderDetailImplTest {

	@Mock
	private OrderDetailRepository orderDetailRep; 

	@Mock
	private IMappingService mapping;
	
	@Mock
	private StoreRepo storeRepo;
	
	@Mock
	private ProductRepo productRepo;
	
	
	@InjectMocks
	private OrderDetailImpl orderDetailImpl;

	
	

	
	
	@Test
	void OrderDetailImpl() throws JsonProcessingException, Exception {

     //object to return
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
		
//		Pageable paging = PageRequest.of(1, 1);
		Page<OrderDetail>  page=new PageImpl<>(ordersDetail);
		//to mock orderDetailRep.findByUserId
		when(orderDetailRep.findByUserId(Mockito.anyInt(),Mockito.any())).thenReturn(page);
		//to mock mapping.mappingOrderDetail
		when(mapping.mappingOrderDetail(ordersDetail)).thenReturn(orderDto);

		OrderDetailDto  response = orderDetailImpl.orderdetails(1, 1, 1);
		
		
		
		assertAll(
    			()->assertEquals("ferre",response.getData().get(0).getStoreName()),
    			()->assertEquals(1.2,response.getData().get(0).getTotalPrice())
    			);
		

	}
	
	
	
	@Test
	void OrderDetailDtoEmpty() throws JsonProcessingException, Exception {
		List<OrderDetail> ordersDetail2 = new ArrayList<>();


		when(orderDetailRep.findByUserId(Mockito.anyInt(),Mockito.any())).thenReturn(new PageImpl<>(ordersDetail2));
		
		assertThrows(OrderDetailNotFoundException.class, ()-> orderDetailImpl.orderdetails(1, 1, 1));

		
	}
	
	
	@Test
	void saveOrderDetails() throws JsonProcessingException, Exception {
		Store store = new Store();
		store.setStoreId(1);
		
		Product pro= new Product();
		pro.setProductId(1);
		pro.setStore(store);
		pro.setProductPrice(22.5);
		
		when(storeRepo.findById(1)).thenReturn(Optional.of(store));
		when(productRepo.findById(1)).thenReturn(Optional.of(pro));

		
		List<ProductListOrderDetailDTO> pl=new ArrayList<>();
		ProductListOrderDetailDTO plDto=new ProductListOrderDetailDTO();
		plDto.setProductId(1);
		plDto.setProductPrice(22.5);
		plDto.setQuantity(5);
		pl.add(plDto);
		
		OrderRequestDTO or= new OrderRequestDTO();
		or.setStoreId(1);
		or.setProductList(pl);
		OrderResponseDTO resp=orderDetailImpl.saveOrderDetails(or);

		
		assertEquals(200, resp.getStatusCode());

		
	}
	
	@Test
	void saveOrderDetailsStoreEmpty() throws JsonProcessingException, Exception {
		OrderRequestDTO or= new OrderRequestDTO();
		or.setStoreId(1);
		when(storeRepo.findById(1)).thenReturn(Optional.empty());
		
		assertThrows(StoreNotFoundException.class, ()->orderDetailImpl.saveOrderDetails(or));

		
	}
	
	
	@Test
	void saveOrderDetailsproductRepoNotFound() throws JsonProcessingException, Exception {
		Store store = new Store();
		store.setStoreId(1);
		
		Product pro= new Product();
		pro.setProductId(1);
		pro.setStore(store);
		pro.setProductPrice(22.5);
		
		when(storeRepo.findById(1)).thenReturn(Optional.of(store));
		when(productRepo.findById(1)).thenReturn(Optional.empty());

		
		List<ProductListOrderDetailDTO> pl=new ArrayList<>();
		ProductListOrderDetailDTO plDto=new ProductListOrderDetailDTO();
		plDto.setProductId(1);
		plDto.setProductPrice(22.5);
		plDto.setQuantity(5);
		pl.add(plDto);
		
		OrderRequestDTO or= new OrderRequestDTO();
		or.setStoreId(1);
		or.setProductList(pl);
//		OrderResponseDTO resp=orderDetailImpl.saveOrderDetails(or);

		assertThrows(ProductNotFoundException.class, ()->orderDetailImpl.saveOrderDetails(or));

	

		
	}
	
	
	@Test
	void storeIdDifferent() throws JsonProcessingException, Exception {
		Store store = new Store();
		store.setStoreId(1);
		
		Store store2 = new Store();
		store.setStoreId(10);
		
		Product pro= new Product();
		pro.setProductId(1);
		pro.setStore(store2);
		pro.setProductPrice(22.5);
		
		when(storeRepo.findById(1)).thenReturn(Optional.of(store));
		when(productRepo.findById(1)).thenReturn(Optional.of(pro));

		
		List<ProductListOrderDetailDTO> pl=new ArrayList<>();
		ProductListOrderDetailDTO plDto=new ProductListOrderDetailDTO();
		plDto.setProductId(1);
		plDto.setProductPrice(22.5);
		plDto.setQuantity(5);
		pl.add(plDto);
		
		OrderRequestDTO or= new OrderRequestDTO();
		or.setStoreId(1);
		or.setProductList(pl);

		assertThrows(ProductNotFoundException.class, ()->orderDetailImpl.saveOrderDetails(or));

	

		
	}
	
	
	@Test
	void productPriceDifferent() throws JsonProcessingException, Exception {
		Store store = new Store();
		store.setStoreId(1);
		
	
		
		Product pro= new Product();
		pro.setProductId(1);
		pro.setStore(store);
		pro.setProductPrice(44.6);
		
		when(storeRepo.findById(1)).thenReturn(Optional.of(store));
		when(productRepo.findById(1)).thenReturn(Optional.of(pro));

		
		List<ProductListOrderDetailDTO> pl=new ArrayList<>();
		ProductListOrderDetailDTO plDto=new ProductListOrderDetailDTO();
		plDto.setProductId(1);
		plDto.setProductPrice(22.5);
		plDto.setQuantity(5);
		pl.add(plDto);
		
		OrderRequestDTO or= new OrderRequestDTO();
		or.setStoreId(1);
		or.setProductList(pl);
//		OrderResponseDTO resp=orderDetailImpl.saveOrderDetails(or);

		assertThrows(ProductNotFoundException.class, ()->orderDetailImpl.saveOrderDetails(or));

	

		
	}
	
	
	
	




}
