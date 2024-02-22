package com.alpha.bankApp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.AccountStatementDto;
import com.alpha.bankApp.dto.StatementDto;
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

	@GetMapping("/passBook")
	public ResponseEntity<ResponseStructure<List<StatementDto>>> findAccountStatement(@PathVariable String version,
			@RequestParam String accountNumber) {
		if (version.equals("v1")) {
			return transactionService.findAccountStatement(accountNumber);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	/*
	 * @PostMapping("/getAccountStatement") public
	 * ResponseEntity<ResponseStructure<List<StatementDto>>>
	 * findAccountStatement(@PathVariable String version,
	 * 
	 * @RequestBody AccountStatementDto accountStatementDto) { if
	 * (version.equals("v1")) { return
	 * transactionService.findAccountStatement(accountStatementDto); } throw new
	 * VersionUnauthorizedException("Not An Authorized Version"); }
	 * 
	 * @PostMapping("/getAccountStatement/downloadExcel") public
	 * ResponseEntity<byte[]> findAccountStatementByExcelFormat(@PathVariable String
	 * version,
	 * 
	 * @RequestBody AccountStatementDto accountStatementDto) { if
	 * (version.equals("v1")) { return
	 * transactionService.findAccountStatementByExcelFormat(accountStatementDto); }
	 * throw new VersionUnauthorizedException("Not An Authorized Version"); }
	 * 
	 * @PostMapping("/getAccountStatement/downloadPDF") public
	 * ResponseEntity<byte[]> findAccountStatementByPDFFormat(@PathVariable String
	 * version,
	 * 
	 * @RequestBody AccountStatementDto accountStatementDto) { if
	 * (version.equals("v1")) { return
	 * transactionService.findAccountStatementByPDFFormat(accountStatementDto); }
	 * throw new VersionUnauthorizedException("Not An Authorized Version"); }
	 */

	@GetMapping("/getAccountStatement")
	public ResponseEntity<ResponseStructure<List<StatementDto>>> findAccountStatement(@PathVariable String version,
			@RequestParam String accountNumber, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		if (version.equals("v1")) {
			return transactionService.findAccountStatement(new AccountStatementDto(accountNumber, startDate, endDate));
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAccountStatement/downloadExcel")
	public ResponseEntity<byte[]> findAccountStatementByExcelFormat(@PathVariable String version,
			@RequestParam String accountNumber, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		if (version.equals("v1")) {
			return transactionService
					.findAccountStatementByExcelFormat(new AccountStatementDto(accountNumber, startDate, endDate));
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAccountStatement/downloadPDF")
	public ResponseEntity<byte[]> findAccountStatementByPDFFormat(@PathVariable String version,
			@RequestParam String accountNumber, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
		if (version.equals("v1")) {
			return transactionService
					.findAccountStatementByPDFFormat(new AccountStatementDto(accountNumber, startDate, endDate));
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
