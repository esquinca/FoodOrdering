package com.foodordering.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodordering.demo.constants.Constants;
import com.foodordering.demo.dto.UserDetailsDTO;
import com.foodordering.demo.dto.request.UserReq;
import com.foodordering.demo.exception.ErrorResponse;
import com.foodordering.demo.exception.OrderDetailNotFoundException;
import com.foodordering.demo.exception.UserNotFoundException;
import com.foodordering.demo.service.IUserService;
import com.foodordering.demo.service.OrderDetailService;

@RestController
@RequestMapping("/users/v1")
public class UserController {
	@Autowired
	private IUserService userService;

	@Autowired
	private OrderDetailService orderDetailService;

	@PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> users(@Valid @RequestBody UserReq userReq) {
		

		return new ResponseEntity<>(userService.findByNameAndPassword(userReq.getName(), userReq.getPassword()),
				HttpStatus.OK);

	}


	@GetMapping(value = "/users/{userId}/orderdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> orderdetails(@PathVariable Integer userId,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {

		System.out.println("osvaldo");

		return new ResponseEntity<>(orderDetailService.orderdetails(userId, pageNo, pageSize), HttpStatus.OK);

	}
	
	@GetMapping("/all_projections/{username}")
	public ResponseEntity<UserDetailsDTO> getAllUsersWithProjection(@PathVariable String username){
		UserDetailsDTO userDetailsDto = userService.getAllUsers(username);
		return new ResponseEntity<UserDetailsDTO>(userDetailsDto, HttpStatus.OK);
	

	} 


}
