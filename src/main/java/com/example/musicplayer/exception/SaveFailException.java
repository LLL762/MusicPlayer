package com.example.musicplayer.exception;

public class SaveFailException extends RuntimeException {

	/**
	 *
	 */
	public SaveFailException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SaveFailException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public SaveFailException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public SaveFailException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public SaveFailException(Throwable cause) {
		super(cause);

	}

}
