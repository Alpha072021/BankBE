package com.alpha.bankApp.exception;

@SuppressWarnings("serial")
public class BankNotAssignedMDException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BankNotAssignedMDException(String message) {
		super();
		this.message = message;
	}

}
