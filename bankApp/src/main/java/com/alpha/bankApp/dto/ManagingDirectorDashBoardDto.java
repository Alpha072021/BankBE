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
@AllArgsConstructor
@NoArgsConstructor
public class ManagingDirectorDashBoardDto {
	private long totalBranches;
	private long totalAccounts;
	private long totalemployees;
	private double totalDeposits;
	private double totalFundTransfer;
	private List<RevenueDto> revenues;
	private AccountHoldersDto accountHolders;

}
