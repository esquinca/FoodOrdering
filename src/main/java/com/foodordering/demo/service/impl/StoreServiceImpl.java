package com.foodordering.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodordering.demo.dto.StoreDetails;
import com.foodordering.demo.dto.StoreResponseDTO;
import com.foodordering.demo.entity.Store;
import com.foodordering.demo.repo.StoreRepo;
import com.foodordering.demo.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService{
	@Autowired
	StoreRepo storeRepo;

	@Override
	public StoreResponseDTO getAllStoreDetails() {
		 List<Store> storeList = storeRepo.findAll();
		 
		 List<StoreDetails> storeDetailList = storeList.stream().map(store -> {
			 StoreDetails storeDetail = new StoreDetails();
			 BeanUtils.copyProperties(store, storeDetail);
			 return storeDetail;
		 }).collect(Collectors.toList());
		 
		 //ResponseDTO responseDTO = new ResponseDTO("Store Details Fetch Success", 200);
		 
		 StoreResponseDTO storeResponseDTO = new StoreResponseDTO("Store details fetch success", 200);
		 storeResponseDTO.setStoreList(storeDetailList);
		 //storeResponseDTO.setResponseDTO(responseDTO);
		 
		 return storeResponseDTO;
	}
	
}
