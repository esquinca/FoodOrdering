package com.foodordering.demo.service;


import com.foodordering.demo.dto.ProductRequestDTO;
import com.foodordering.demo.dto.ProductResponseDTO;
import com.foodordering.demo.entity.Product;

public interface ProductService {

	Product saveProductDetails(ProductRequestDTO productRequestDto);

	ProductResponseDTO getProductDetailsByStoreId(Integer storeId);
	
}
