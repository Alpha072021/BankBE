package com.alpha.bankApp.exception;

@SuppressWarnings("serial")
public class EmployeeNotAssingedRoleException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EmployeeNotAssingedRoleException(String message) {
		super();
		this.message = message;
	}

}
