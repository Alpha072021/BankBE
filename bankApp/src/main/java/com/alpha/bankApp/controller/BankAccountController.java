package com.alpha.bankApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BankAccountService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/version/{version}/bankAccounts")
public class BankAccountController {

	private BankAccountService bankAccountService;

	@GetMapping
	public ResponseEntity<ResponseStructure<BankAccount>> getBankAccount(@PathVariable String version,
			@RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return bankAccountService.getBankAccount(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<BankAccount>> updateBankAccountById(@PathVariable String version,
			@RequestParam long bankAccountId, @RequestBody BankAccount bankAccount) {
		if (version.equalsIgnoreCase("v1"))
			return bankAccountService.updateBankAccountById(bankAccountId, bankAccount);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
