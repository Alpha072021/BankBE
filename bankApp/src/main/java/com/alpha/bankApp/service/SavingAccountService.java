package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.SavingAccountDto;
import com.alpha.bankApp.entity.SavingsAccount;
import com.alpha.bankApp.util.ResponseStructure;

public interface SavingAccountService extends AccountService {

	ResponseEntity<ResponseStructure<List<SavingAccountDto>>> getAllSavingAccountsByBranchId(String branchId);

	ResponseEntity<ResponseStructure<List<SavingAccountDto>>> getAllSavingAccountsBybankId(String bankId);

	ResponseEntity<ResponseStructure<SavingAccountDto>> getSavingAccountByAccountNumber(String accountNumber);

	ResponseEntity<ResponseStructure<SavingsAccount>> saveAccount(String userId, String branchId,
			SavingsAccount account);

}
