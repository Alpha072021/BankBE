package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.BankDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BankService;
import com.alpha.bankApp.util.BankUtil;
import com.alpha.bankApp.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/version/{version}/banks")
public class BankController {
	@Autowired
	private BankService bankService;
	@Autowired
	private BankUtil utill;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<ResponseStructure<Bank>> createBank(@PathVariable String version,
			@RequestBody @Valid Bank bank) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.createBank(utill.convertBankInfo(bank));
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping("/bankId/{bankId}")
	public ResponseEntity<ResponseStructure<Bank>> updateBank(@PathVariable String version, @PathVariable String bankId,
			@RequestBody Bank bank) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.updateBank(bankId, bank);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/bankId/{bankId}")
	public ResponseEntity<ResponseStructure<Bank>> getBank(@PathVariable String version, @PathVariable String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.getBank(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping("/bankId/{bankId}")
	public ResponseEntity<ResponseStructure<String>> deleteBank(@PathVariable String version,
			@PathVariable String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.deleteBank(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/bankName/{bankName}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByName(@PathVariable String version,
			@PathVariable String bankName) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.getBankByName(bankName.toLowerCase());
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	// Branch will be assigned will creating the branch
	/*
	 * @PatchMapping("/bankId/{bankId}/branchId/{branchId}") public
	 * ResponseEntity<ResponseStructure<String>> mapBranch(@PathVariable String
	 * bankId,
	 * 
	 * @PathVariable String branchId) { return bankService.mapBranch(bankId,
	 * branchId); }
	 */

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseStructure<List<BankDto>>> getAllBank(@PathVariable String version) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.getAllBank();
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAllUnAssigned")
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks(@PathVariable String version) {
		if (version.equalsIgnoreCase("v1"))
			return bankService.getAllBanks();
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
