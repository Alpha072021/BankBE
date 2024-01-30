package com.alpha.bankApp.service;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.util.ResponseStructure;

public interface BankAccountService {

	ResponseEntity<ResponseStructure<BankAccount>> getBankAccount(String bankId);
}
