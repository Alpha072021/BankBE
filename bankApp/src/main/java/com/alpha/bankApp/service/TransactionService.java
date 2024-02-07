package com.alpha.bankApp.service;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.entity.Deposit;
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.util.ResponseStructure;

public interface TransactionService {
	ResponseEntity<ResponseStructure<String>> deposit(String accountNumber, Deposit deposit);

	ResponseEntity<ResponseStructure<String>> fundTransfer(FundTransfer fundTransfer);
}
