package com.connect.bo.exception;

import java.io.Serializable;

public class ConectividadBoException extends Exception implements Serializable {

	private static final long serialVersionUID = -753338392117570207L;

	public ConectividadBoException() {
		super();
	}
	

	public ConectividadBoException(String message) {
		super(message);
	}

	public ConectividadBoException(Throwable cause) {
		super(cause);
	}

	public ConectividadBoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConectividadBoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
