package com.wmcalyj.exceptions.message;

public class InvalidMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8541423043293923520L;

	public InvalidMessageException() {
		super();
	}

	public InvalidMessageException(String message) {
		super(message);
	}

	public InvalidMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessageException(Throwable cause) {
		super(cause);
	}

	protected InvalidMessageException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
