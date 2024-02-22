package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.AccountStatementDto;
import com.alpha.bankApp.dto.StatementDto;
import com.alpha.bankApp.entity.Deposit;
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.util.ResponseStructure;

public interface TransactionService {
	ResponseEntity<ResponseStructure<String>> deposit(String accountNumber, Deposit deposit);

	ResponseEntity<ResponseStructure<String>> fundTransfer(FundTransfer fundTransfer);

	ResponseEntity<ResponseStructure<List<StatementDto>>> findAccountStatement(String accountNumber);

	ResponseEntity<ResponseStructure<List<StatementDto>>> findAccountStatement(AccountStatementDto accountStatementDto);

	/*
	 * This function will create an MS Excel file using the provided account number,
	 * start date, and end date.
	 */
	ResponseEntity<byte[]> findAccountStatementByExcelFormat(AccountStatementDto accountStatementDto);

	/*
	 * This function will create an PDF file using the provided account number,
	 * start date, and end date.
	 */
	ResponseEntity<byte[]> findAccountStatementByPDFFormat(AccountStatementDto accountStatementDto);
}
