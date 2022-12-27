package com.booking.brownfield.exception;

public class RecordAlreadyPresentException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordAlreadyPresentException(String msg) {
		super(msg);
	}

}
