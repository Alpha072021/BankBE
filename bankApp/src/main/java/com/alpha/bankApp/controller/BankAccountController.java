package com.alpha.bankApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BankAccountService;
import com.alpha.bankApp.util.ResponseStructure;

@RestController
@RequestMapping("api/version/{version}/bankAccounts")
public class BankAccountController {

	private BankAccountService bankAccountService;

	@GetMapping
	public ResponseEntity<ResponseStructure<BankAccount>> getBankAccount(String version, String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return bankAccountService.getBankAccount(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
