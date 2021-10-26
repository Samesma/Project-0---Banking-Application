package com.revature.exceptions;

public class InvalidAccountNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public InvalidAccountNumberException() {
		super("The account number is invalid");
	}
}
