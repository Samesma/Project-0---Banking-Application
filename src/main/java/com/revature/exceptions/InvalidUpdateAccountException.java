package com.revature.exceptions;

public class InvalidUpdateAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public InvalidUpdateAccountException()
	{
		super("Updating the account failed");
	}

}
