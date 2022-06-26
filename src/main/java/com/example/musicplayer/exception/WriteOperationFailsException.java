package com.example.musicplayer.exception;

public class WriteOperationFailsException extends RuntimeException {

	/**
	 *
	 */
	public WriteOperationFailsException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public WriteOperationFailsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public WriteOperationFailsException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public WriteOperationFailsException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public WriteOperationFailsException(Throwable cause) {
		super(cause);

	}

}
