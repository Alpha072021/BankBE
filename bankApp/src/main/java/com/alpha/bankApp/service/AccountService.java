package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.AccountDto;
import com.alpha.bankApp.util.ResponseStructure;

public interface AccountService {
	ResponseEntity<ResponseStructure<List<AccountDto>>> getAllAccountsByBranchId(String branchId);

	ResponseEntity<ResponseStructure<List<AccountDto>>> getAllAccountsBybankId(String bankId);

	ResponseEntity<ResponseStructure<String>> deleteAccountByAccountNumber(String accountNumber);

	ResponseEntity<ResponseStructure<AccountDto>> getAccountByAccountNumber(String accountNumber);

	ResponseEntity<ResponseStructure<AccountDto>> updateAccount(AccountDto accountDto);

}
