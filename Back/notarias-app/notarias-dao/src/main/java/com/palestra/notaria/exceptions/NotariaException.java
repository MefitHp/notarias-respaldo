package com.palestra.notaria.exceptions;

import java.io.Serializable;

public class NotariaException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public NotariaException() {
		super();
	}
	

	public NotariaException(String message) {
		super(message);
	}

	public NotariaException(Throwable cause) {
		super(cause);
	}

	public NotariaException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotariaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
