package com.foodordering.demo.exception;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.foodordering.demo.constants.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(StoreNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(StoreNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.STORE_NOT_FOUND);
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
		
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(UserNotFoundException ex) {

		System.out.println("ErrorResponse:" + ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.USER_NOT_FOUND);
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);

	}
	
	@ExceptionHandler(OrderDetailNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleExceptionOrderDetail(OrderDetailNotFoundException ex) {

		System.out.println("ErrorResponse:" + ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.USER_NOT_FOUND);
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);

	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleExceptionValid(MethodArgumentNotValidException ex) {

		ValidationErrorResponse errorResponse = new ValidationErrorResponse("invalid arguments parameters", Constants.INVALID_ARGS);
		ex.getBindingResult().getFieldErrors().stream().forEach(error->{
			System.out.println("into foreach");
			errorResponse.getInvalidArguments().put(error.getField(), error.getDefaultMessage());
			
		});
		
		errorResponse.setDate(new Date());
		return new ResponseEntity<ValidationErrorResponse>(errorResponse, HttpStatus.OK);

	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleExceptionProductNotFound(ProductNotFoundException ex) {


		System.out.println("ErrorResponse:" + ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Constants.USER_NOT_FOUND);
		errorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);

	
	}
}
