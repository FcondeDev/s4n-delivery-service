package com.s4n.delivery.exception;

public class ServiceException extends Exception {


	private static final long serialVersionUID = -5150251107164212034L;
	private final String message;


	public ServiceException(String message) {
		this.message = message;
	}
	
	
	@Override
	public String getMessage() {
		return message;
	}



	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

}
