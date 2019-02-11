package com.wallet.exception;

public class CustomerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Customer Not Exist";
	}

	
}
