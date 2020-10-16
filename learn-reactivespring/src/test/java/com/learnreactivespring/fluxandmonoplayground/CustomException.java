package com.learnreactivespring.fluxandmonoplayground;

public class CustomException extends Throwable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 704959818101178888L;
	
	private final String message;
	
	public CustomException(Throwable e) {
		this.message= e.getMessage();
	}

	public String getMessage() {
		return message;
	}
	
	

}
