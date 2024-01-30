package com.alpha.bankApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dto.BankDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.exception.AdminNotHaveAnyBankException;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.service.BankService;
import com.alpha.bankApp.util.BankUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BankServiceImpl implements BankService {
	@Autowired
	private BankDao bankDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private BankUtil bankUtil;

	/**
	 * @param the Bank Object to save
	 * @return the ResponseEntity<ResponseStructure<Bank>>
	 */
	@Override
	public ResponseEntity<ResponseStructure<Bank>> createBank(Bank bank) {

		// bank.setBankId(generateBankId(bank));
		ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Created");
		responseStructure.setData(bankDao.createBank(bank));
		return new ResponseEntity<ResponseStructure<Bank>>(responseStructure, HttpStatus.CREATED);
	}

	// To Generate Custom BankId
	/*
	 * private String generateBankId(Bank bank) { String prifix =
	 * bank.getBankName(); String sufix = ""; List<Bank> banks =
	 * bankDao.getBankByName(bank.getBankName()); if (banks != null &&
	 * !(banks.isEmpty())) { if (banks.size() < 9) { sufix = "0000" + (banks.size()
	 * + 1); } else if (banks.size() < 99) { sufix = "000" + (banks.size() + 1); }
	 * else if (banks.size() < 999) { sufix = "00" + (banks.size() + 1); } else if
	 * (banks.size() < 9999) { sufix = "0" + (banks.size() + 1); } else { sufix = ""
	 * + (banks.size() + 1); } } else { sufix = "00001"; } return prifix + sufix; }
	 */
	/**
	 * @param the Bank Object and bank Id to modify the bank info
	 * @return the ResponseEntity<ResponseStructure<Bank>> if bank exist else throws
	 *         BankNotFoundException
	 */

	@Override
	public ResponseEntity<ResponseStructure<Bank>> updateBank(String bankId, Bank bank) {
		bank = bankUtil.modifiedBank(bankId, bank);
		bank = bankDao.updateBank(bankId, bank);
		if (bank != null) {
			ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Modified");
			responseStructure.setData(bank);
			return new ResponseEntity<ResponseStructure<Bank>>(responseStructure, HttpStatus.OK);
		}
		throw new BankNotFoundException("Bank with the Given Id = " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Bank>> getBank(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Found");
			responseStructure.setData(bank);
			return new ResponseEntity<ResponseStructure<Bank>>(responseStructure, HttpStatus.OK);
		}
		throw new BankNotFoundException("Bank with the Given Id = " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteBank(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			bankDao.deleteBank(bankId);
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
			responseStructure.setMessage("Removed");
			responseStructure.setData("Bank With the given Id = " + bankId + " Removed");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
		}
		throw new BankNotFoundException("Bank with the Given Id = " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByName(String bankName) {
		List<Bank> bank = bankDao.getBankByName(bankName);
		if (bank != null && !bank.isEmpty()) {
			ResponseStructure<List<Bank>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Found");
			responseStructure.setData(bank);
			return new ResponseEntity<ResponseStructure<List<Bank>>>(responseStructure, HttpStatus.OK);
		}
		throw new BankNotFoundException("Bank with the Given Name = " + bankName + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> mapBranch(String bankId, String branchId) {
		Bank bank = bankDao.getBank(bankId);
		Branch branch = branchDao.getBranch(branchId);
		if (bank == null) {
			throw new BankNotFoundException("Bank with the Given BankId = " + bankId + " Not Found");
		} else if (branch == null) {
			throw new BranchNotFoundException("Bank with the Given BankId = " + bankId + " Not Found");
		} else
			bankDao.mapBranch(bankId, branchId);
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Branch Mapped to Bank ");
		responseStructure.setData("Branch with the Id =" + branchId + " is mapped to Bank With the Id = " + bankId);
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<BankDto>>> getAllBank() {
		List<Bank> banks = bankDao.getAllBank();
		if (banks != null) {
			ResponseStructure<List<BankDto>> responseStructure = new ResponseStructure<>();
			responseStructure.setData(bankUtil.getBankDto(banks));
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<BankDto>>>(responseStructure, HttpStatus.OK);
		}
		throw new AdminNotHaveAnyBankException("Banks Are Not At Created");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks() {
		List<Bank> banks = bankDao.getAllBanks();

		if (banks != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("exposedHeaders", "Access-Control-Allow-Origin");
			headers.set("exposedHeaders1", "Access-Control-Allow-Credentials");
			ResponseStructure<List<Bank>> responseStructure = new ResponseStructure<>();
			responseStructure.setData(banks);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.ok().headers(headers).body(responseStructure);
		}
		throw new AdminNotHaveAnyBankException("All Banks Are Assigned An ManagingDirector Create New Bank");
	}

}
