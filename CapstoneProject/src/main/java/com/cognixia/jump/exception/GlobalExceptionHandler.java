package com.cognixia.jump.exception;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> validationException(MethodArgumentNotValidException ex, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		return ResponseEntity.status(400).body(errorDetails);
	}
	
	@ExceptionHandler(IncorrectLoginException.class)
	public ResponseEntity<?> resourceNotFoundException(IncorrectLoginException ex, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		return ResponseEntity.status(404).body(errorDetails);
	}
	
	@ExceptionHandler(NoSuchUserException.class)
	public ResponseEntity<?> resourceNotFoundException(NoSuchUserException ex, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
		return ResponseEntity.status(404).body(errorDetails);
	}
	
}
