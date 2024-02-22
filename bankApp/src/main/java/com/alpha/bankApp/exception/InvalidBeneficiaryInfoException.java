package com.alpha.bankApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidBeneficiaryInfoException extends RuntimeException {
	private String message;
}
