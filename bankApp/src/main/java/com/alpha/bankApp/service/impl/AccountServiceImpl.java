package com.alpha.bankApp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dto.AccountDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.exception.BankNotAssignedBranchException;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.BranchNotAssignedException;
import com.alpha.bankApp.exception.CustomersNotHaveAccount;
import com.alpha.bankApp.service.AccountService;
import com.alpha.bankApp.util.AccountUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountUtil accountUtil;
	@Autowired
	private BankDao bankDao;

	@Override
	public ResponseEntity<ResponseStructure<List<AccountDto>>> getAllAccountsByBranchId(String branchId) {
		List<Account> accounts = accountDao.getAllAccounts(branchId);
		if (accounts != null && !accounts.isEmpty()) {
			ResponseStructure<List<AccountDto>> structure = new ResponseStructure<>();
			structure.setData(accountUtil.getAccountDto(accounts));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<AccountDto>>>(structure, HttpStatus.OK);
		}
		throw new BranchNotAssignedException("Branch Not have any Account");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<AccountDto>>> getAllAccountsBybankId(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			if (bank.getBranches() != null && !bank.getBranches().isEmpty()) {
				List<Account> accounts = new ArrayList<>();
				for (Branch branch : bank.getBranches()) {
					if (branch.getAccounts() != null && !branch.getAccounts().isEmpty()) {
						accounts.addAll(branch.getAccounts());
					}
				}
				// After this for-loop we will receive all the account Belongs to bank
				if (!accounts.isEmpty()) {
					ResponseStructure<List<AccountDto>> structure = new ResponseStructure<>();
					structure.setData(accountUtil.getAccountDto(accounts));
					structure.setMessage("Found");
					structure.setStatusCode(HttpStatus.OK.value());
					return new ResponseEntity<ResponseStructure<List<AccountDto>>>(structure, HttpStatus.OK);
				}
				throw new CustomersNotHaveAccount("Customers Not Have Any Account in the Given Bank");
			}
			throw new BankNotAssignedBranchException("Bank Not Have Any Branches");
		}
		throw new BankNotFoundException("Bank With the Given Id " + bankId + " Not exsits");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteAccountByAccountNumber(String accountNumber) {
		if (accountDao.removeAccount(accountNumber)) {
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setData("Removed");
			structure.setMessage("Successfully Removed");
			structure.setStatusCode(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NO_CONTENT);
		}
		throw new CustomersNotHaveAccount("Account With the Given Number " + accountNumber + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<AccountDto>> getAccountByAccountNumber(String accountNumber) {
		Account account = accountDao.getAccountByAccountNumber(accountNumber);
		if (account != null) {
			ResponseStructure<AccountDto> structure = new ResponseStructure<>();
			structure.setData(accountUtil.getAccountDto(account));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<AccountDto>>(structure, HttpStatus.OK);
		}
		throw new CustomersNotHaveAccount("Account With the Given Account-Number " + accountNumber + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<AccountDto>> updateAccount(AccountDto accountDto) {
		// TO Update the Account Info
		Account account = accountUtil.modifiedAccount(accountDto);
		ResponseStructure<AccountDto> structure = new ResponseStructure<>();
		structure.setData(accountUtil.getAccountDto(accountDao.updateAccount(accountDto.getAccountNumber(), account)));
		structure.setMessage("Modified");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<AccountDto>>(structure, HttpStatus.OK);
	}

}
