package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.CurrentAccountDto;
import com.alpha.bankApp.entity.CurrentAccount;
import com.alpha.bankApp.util.ResponseStructure;

public interface CurrentAccountService extends AccountService {
	ResponseEntity<ResponseStructure<List<CurrentAccountDto>>> getAllCurentAccountsByBranchId(String branchId);

	ResponseEntity<ResponseStructure<List<CurrentAccountDto>>> getAllCurrentAccountsBybankId(String bankId);

	ResponseEntity<ResponseStructure<CurrentAccountDto>> getCurrentAccountByAccountNumber(String accountNumber);

	ResponseEntity<ResponseStructure<CurrentAccount>> saveAccount(String userId, String branchId,
			CurrentAccount account);
}
