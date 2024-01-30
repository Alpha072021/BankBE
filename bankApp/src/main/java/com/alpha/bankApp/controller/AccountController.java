package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.AccountDto;
import com.alpha.bankApp.dto.CurrentAccountDto;
import com.alpha.bankApp.dto.SavingAccountDto;
import com.alpha.bankApp.entity.CurrentAccount;
import com.alpha.bankApp.entity.SavingsAccount;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.AccountService;
import com.alpha.bankApp.service.CurrentAccountService;
import com.alpha.bankApp.service.SavingAccountService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/version/{version}/accounts")
public class AccountController {
	@Autowired
	@Qualifier("accountServiceImpl")
	private AccountService accountService;
	@Autowired
	@Qualifier("savingAccountServiceImpl")
	private SavingAccountService savingAccountService;
	@Autowired
	@Qualifier("currentAccountServiceImpl")
	private CurrentAccountService currentAccountService;

	@GetMapping("/getAllAccounts/branchId")
	public ResponseEntity<ResponseStructure<List<AccountDto>>> getAllAccountsByBranchId(@PathVariable String version,
			@RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return accountService.getAllAccountsByBranchId(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAllAccounts/bankId")
	public ResponseEntity<ResponseStructure<List<AccountDto>>> getAllAccountsBybankId(@PathVariable String version,
			@RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return accountService.getAllAccountsBybankId(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping("/remove")
	public ResponseEntity<ResponseStructure<String>> deleteAccountByAccountNumber(@PathVariable String version,
			@RequestParam String accountNumber) {
		if (version.equalsIgnoreCase("v1"))
			return accountService.deleteAccountByAccountNumber(accountNumber);
		throw new VersionUnauthorizedException("Not An Authorized Version");

	}

	@GetMapping("/getAccountByAccountNumber")
	public ResponseEntity<ResponseStructure<AccountDto>> getAccountByAccountNumber(@PathVariable String version,
			@RequestParam String accountNumber) {
		if (version.equalsIgnoreCase("v1"))
			return accountService.getAccountByAccountNumber(accountNumber);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PostMapping("/saving")
	public ResponseEntity<ResponseStructure<SavingsAccount>> saveAccount(@PathVariable String version,
			@RequestParam String userId, @RequestParam String branchId, @RequestBody SavingsAccount account) {
		if (version.equalsIgnoreCase("v1"))
			return savingAccountService.saveAccount(userId, branchId, account);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/saving/getAllAccounts/branchId")
	public ResponseEntity<ResponseStructure<List<SavingAccountDto>>> getAllSavingAccountsByBranchId(
			@PathVariable String version, @RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return savingAccountService.getAllSavingAccountsByBranchId(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/saving/getAllAccounts/bankId")
	public ResponseEntity<ResponseStructure<List<SavingAccountDto>>> getAllSavingAccountsBybankId(
			@PathVariable String version, @RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return savingAccountService.getAllSavingAccountsBybankId(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/saving/getAccountByAccountNumber")
	public ResponseEntity<ResponseStructure<SavingAccountDto>> getSavingAccountByAccountNumber(
			@PathVariable String version, @RequestParam String accountNumber) {
		if (version.equalsIgnoreCase("v1"))
			return savingAccountService.getSavingAccountByAccountNumber(accountNumber);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PostMapping("/current")
	public ResponseEntity<ResponseStructure<CurrentAccount>> saveAccount(@PathVariable String version,
			@RequestParam String userId, @RequestParam String branchId, @RequestBody CurrentAccount account) {
		if (version.equalsIgnoreCase("v1"))
			return currentAccountService.saveAccount(userId, branchId, account);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/current/getAllAccounts/branchId")
	public ResponseEntity<ResponseStructure<List<CurrentAccountDto>>> getAllCurrentAccountsByBranchId(
			@PathVariable String version, @RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return currentAccountService.getAllCurentAccountsByBranchId(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/current/getAllAccounts/bankId")
	public ResponseEntity<ResponseStructure<List<CurrentAccountDto>>> getAllCurrentAccountsBybankId(
			@PathVariable String version, @RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return currentAccountService.getAllCurrentAccountsBybankId(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/current/getAccountByAccountNumber")
	public ResponseEntity<ResponseStructure<CurrentAccountDto>> getCurrentAccountByAccountNumber(
			@PathVariable String version, @RequestParam String accountNumber) {
		if (version.equalsIgnoreCase("v1"))
			return currentAccountService.getCurrentAccountByAccountNumber(accountNumber);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
