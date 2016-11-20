package com.stringregistry.api;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.stringregistry.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		setErrorIndication(exception, errorResponse);
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(errorResponse.getErrorCode()));
	}
	private void setErrorIndication(Exception exception, ErrorResponse errorResponse){
		
		errorResponse.setMessage("An error ocurred while processing your request. "+ exception.getMessage());
		logger.error(exception.toString(), exception);
		
		if (exception instanceof NoHandlerFoundException || 
			exception instanceof MethodArgumentTypeMismatchException ||
		    exception instanceof HttpMessageNotReadableException) {
			
			errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
			
		} else if (exception instanceof EntityExistsException) {
			
			errorResponse.setErrorCode(HttpStatus.CONFLICT.value());
			
		} else {
			
			errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
}
