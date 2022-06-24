package com.example.musicplayer.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 *
	 */
	public ResourceNotFoundException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public ResourceNotFoundException(Throwable cause) {
		super(cause);

	}

}
