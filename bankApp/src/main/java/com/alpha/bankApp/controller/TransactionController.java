package com.alpha.bankApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.entity.Deposit;
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.TransactionService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("api/version/{version}/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@Hidden
	@PostMapping("/deposit")
	public ResponseEntity<ResponseStructure<String>> deposit(@PathVariable String version,
			@RequestParam String accountNumber, @RequestBody Deposit deposit) {
		if (version.equals("v1")) {
			return transactionService.deposit(accountNumber, deposit);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PostMapping("/fundTransfer")
	public ResponseEntity<ResponseStructure<String>> fundTransfer(@PathVariable String version,
			@RequestBody FundTransfer fundTransfer) {
		if (version.equals("v1")) {
			return transactionService.fundTransfer(fundTransfer);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
