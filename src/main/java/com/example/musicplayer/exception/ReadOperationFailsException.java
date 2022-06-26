package com.example.musicplayer.exception;

public class ReadOperationFailsException extends RuntimeException {

	/**
	 *
	 */
	public ReadOperationFailsException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ReadOperationFailsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public ReadOperationFailsException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public ReadOperationFailsException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public ReadOperationFailsException(Throwable cause) {
		super(cause);

	}

}