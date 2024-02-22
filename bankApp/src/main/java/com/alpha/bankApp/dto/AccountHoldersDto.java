package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountHoldersDto {
	private long totalSavingAccounts;
	private long totalCurrentAccounts;
	private long totalLoanAccounts;
	private long totalCreditAccounts;
}
