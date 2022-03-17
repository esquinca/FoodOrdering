package com.foodordering.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foodordering.demo.dto.UserDto;
import com.foodordering.demo.dto.mapping.service.IMappingService;
import com.foodordering.demo.dto.request.UserReq;
import com.foodordering.demo.entity.User;
import com.foodordering.demo.exception.UserNotFoundException;
import com.foodordering.demo.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
 
	@Mock
	private UserRepo userRepo;
	@Mock
	private IMappingService mapping; 
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	
	
	@Test
	@DisplayName("validate user")
	void findByNameAndPassword() {
	 	UserReq userRe = new UserReq();
    	userRe.setName("Osvaldo");
    	userRe.setPassword("123567mambo");
    	
		UserDto us=new UserDto();
		us.setUserId(1);
		us.setMessage("Successful Login");
		us.setStatusCode("200 OK");
		
		User user = new User();
		user.setEmail("osva@12.com");
	/*	user.setAddressList(null);
		user.setPassword(null);
		user.setPhoneNo(null);
		user.setUserId(null);
		user.setUsername(null);*/
    	when(userRepo.findByUsernameAndPassword(userRe.getName(), userRe.getPassword())).thenReturn(Optional.of(user));

    	when(mapping.mappingUSer(user)).thenReturn(us);
//    	when(mapping.mappingUSer(Mockito.any(User.class))).thenReturn(us);
 /*   	when(mapping.mappingUSer(Mockito.any(User.class))).thenAnswer(i->{
    		UserDto us=i.getArgument(0);
    		us.setUserId(1);
    		us.setMessage("Successful Login");
    		us.setStatusCode("200 OK");
    		return us;
    	});*/

    	UserDto useDto=userServiceImpl.findByNameAndPassword(userRe.getName(), userRe.getPassword());
    	assertAll(()->assertNotNull(useDto.getMessage()),
    			()->assertNotNull(useDto.getStatusCode()),
    			()->assertNotNull(useDto.getUserId())
    			);
    	
    	assertAll(()->assertEquals("Successful Login",useDto.getMessage()),
    			()->assertEquals("200 OK",useDto.getStatusCode()),
    			()->assertEquals(1,useDto.getUserId())
    			);
	
	}
	
	@Test
	@DisplayName("validate fail user")
	void findByNameAndPasswordFail() {

//    	when(userRepo.findByUsernameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty());
   
//		Exception exception = 
		assertThrows(UserNotFoundException.class, () -> userServiceImpl.findByNameAndPassword("", "22"));
//    	assertEquals(exception.getMessage(), "User not found");

	
	}

}
