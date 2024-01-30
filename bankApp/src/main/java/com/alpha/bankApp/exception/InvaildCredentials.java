/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.exception;

/**
 * @author Vinod Raj
 */

@SuppressWarnings("serial")
public class InvaildCredentials extends RuntimeException {

	private String message = "Invalid Credentials";

	public InvaildCredentials() {

	}

	public InvaildCredentials(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
