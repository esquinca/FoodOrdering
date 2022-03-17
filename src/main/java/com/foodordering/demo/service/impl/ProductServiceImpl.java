package com.foodordering.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodordering.demo.dto.ProductDetails;
import com.foodordering.demo.dto.ProductRequestDTO;
import com.foodordering.demo.dto.ProductResponseDTO;
import com.foodordering.demo.entity.Product;
import com.foodordering.demo.entity.ProductCategory;
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.exception.StoreNotFoundException;
import com.foodordering.demo.repo.ProductRepo;
import com.foodordering.demo.repo.StoreRepo;
import com.foodordering.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	StoreRepo storeRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Override
	public Product saveProductDetails(ProductRequestDTO productRequestDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productRequestDto, product);
		
		product.setProductCategory(ProductCategory.valueOf(productRequestDto.getProductCategory()));
		
		
		Optional<Store> storeOptional = storeRepo.findById(productRequestDto.getStoreId());
		
		if(storeOptional.isEmpty()) {
			throw new StoreNotFoundException("Store not found : " + productRequestDto.getStoreId());
		}
		
		product.setStore(storeOptional.get());
		return productRepo.save(product);
	}

	@Override
	public ProductResponseDTO getProductDetailsByStoreId(Integer storeId) {
		
		Optional<Store> filterStore = storeRepo.findById(storeId);
		
		List<Product> productList = productRepo.findByStore(filterStore);
		
		List<ProductDetails> productDetailList = productList.stream().map(product -> {
			ProductDetails productDetails = new ProductDetails();
			BeanUtils.copyProperties(product, productDetails);
			return productDetails;
		}).collect(Collectors.toList());
		
		ProductResponseDTO productResponseDTO = new ProductResponseDTO("Fetch of products by id successfull", 200);
		productResponseDTO.setProductList(productDetailList);
		
		return productResponseDTO;
		
	}

}
