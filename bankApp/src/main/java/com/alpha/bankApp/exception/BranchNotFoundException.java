package com.alpha.bankApp.exception;

@SuppressWarnings("serial")
public class BranchNotFoundException extends RuntimeException {
	private String message;

	public BranchNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
