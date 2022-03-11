package com.foodordering.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodordering.demo.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	
	List<OrderDetail> findByUserId(Integer userId);
	


}
