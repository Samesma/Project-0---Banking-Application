package com.revature.exceptions;

public class InvalidAcountBalanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public InvalidAcountBalanceException()
	{
		super("The account's balance is not enough");
	}

}
