package com.alpha.bankApp.exception;

@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UnauthorizedException(String message) {
		super();
		this.message = message;
	}

}
