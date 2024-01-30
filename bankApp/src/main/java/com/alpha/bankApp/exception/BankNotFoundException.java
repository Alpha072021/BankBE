package com.alpha.bankApp.exception;

@SuppressWarnings("serial")
public class BankNotFoundException extends RuntimeException {
	private String message;

	public BankNotFoundException(String message) {
		this.message = message;
	}

	/**
	 * @return the Exception Message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param the message to set the variable message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
