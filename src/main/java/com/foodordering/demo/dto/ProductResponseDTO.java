package com.foodordering.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductResponseDTO extends ResponseDTO{

	public ProductResponseDTO(String message, int statusCode) {
		super(message, statusCode);
		// TODO Auto-generated constructor stub
	}
	private List<ProductDetails> productList = new ArrayList<>();
	
	
	public List<ProductDetails> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductDetails> productList) {
		this.productList = productList;
	}
}
