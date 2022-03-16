package com.foodordering.demo.service;

import com.foodordering.demo.dto.UserDetailsDTO;
import com.foodordering.demo.dto.UserDto;



public interface IUserService {
	
	 UserDto findByNameAndPassword(String name, String pass) ;

	UserDetailsDTO getAllUsers(String username);
	 
	 
	 

}
