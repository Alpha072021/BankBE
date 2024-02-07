package com.alpha.bankApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("serial")
public class BankAssignedManagingDirectorException extends RuntimeException {
	private String message;

}
