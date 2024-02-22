package com.alpha.bankApp.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchManagerDashBoardDto {
	private long totalSavingAccountNumber;
	private long totalCurrentAccount;
	private long totalCreditCardAccount;
	private double totalDeposits;
	private double totalFundTransfer;
	private List<RevenueDto> revenues;
}
