package com.alpha.bankApp.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.service.BankAccountService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Override
	public ResponseEntity<ResponseStructure<BankAccount>> getBankAccount(String bankId) {
		return null;
	}

}
