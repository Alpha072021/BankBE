package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.BankDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.util.ResponseStructure;

public interface BankService {
	ResponseEntity<ResponseStructure<Bank>> createBank(Bank bank);

	ResponseEntity<ResponseStructure<Bank>> updateBank(String bankId, Bank bank);

	ResponseEntity<ResponseStructure<Bank>> getBank(String bankId);

	ResponseEntity<ResponseStructure<String>> deleteBank(String bankId);

	ResponseEntity<ResponseStructure<List<Bank>>> getBankByName(String bankName);

	ResponseEntity<ResponseStructure<String>> mapBranch(String bankId, String branchId);

	ResponseEntity<ResponseStructure<List<BankDto>>> getAllBank();

	ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks();
}
