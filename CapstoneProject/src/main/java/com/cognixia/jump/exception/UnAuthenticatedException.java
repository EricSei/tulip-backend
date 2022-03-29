package com.cognixia.jump.exception;

public class UnAuthenticatedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public UnAuthenticatedException(String message) {
		super(message);
	}
}
