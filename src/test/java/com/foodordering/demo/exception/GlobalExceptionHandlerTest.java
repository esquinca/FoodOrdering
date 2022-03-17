package com.foodordering.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
	
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Mock
	BindingResult bindingResult;
	

	@Test
	void handleException() {
		UserNotFoundException ex= new UserNotFoundException("user not found");
		ResponseEntity<ErrorResponse> resp=globalExceptionHandler.handleException(ex);
		assertEquals(resp.getBody().getMessage(), "user not found");
	
	}
	
	@Test
	void handleExceptionValid() {
//		when(bindingResult.hasErrors()).thenReturn(false);
		MethodArgumentNotValidException ex= new MethodArgumentNotValidException(null, bindingResult);
		ResponseEntity<ValidationErrorResponse> resp=globalExceptionHandler.handleExceptionValid(ex);
		System.out.println(resp.getBody().getMessage());
		assertEquals(resp.getBody().getMessage(), "invalid arguments parameters");
	
	}

}
