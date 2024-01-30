/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.exception;

/**
 * @author Vinod Raj
 */

@SuppressWarnings("serial")
public class InvalidAuthorizationException extends RuntimeException {

	private String message = "Invalid Authorized User";

	public InvalidAuthorizationException() {
	}

	public InvalidAuthorizationException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
