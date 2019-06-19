package com.connect.dao.exception;

import java.io.Serializable;

public class ConectividadDaoException extends Exception implements Serializable {

	private static final long serialVersionUID = 1433000305928346703L;

	public ConectividadDaoException() {
		super();
	}
	

	public ConectividadDaoException(String message) {
		super(message);
	}

	public ConectividadDaoException(Throwable cause) {
		super(cause);
	}

	public ConectividadDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConectividadDaoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
