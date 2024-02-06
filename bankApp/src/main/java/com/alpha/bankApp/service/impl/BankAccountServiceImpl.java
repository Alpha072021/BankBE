package com.alpha.bankApp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.exception.BankAccountNotFoundException;
import com.alpha.bankApp.service.BankAccountService;
import com.alpha.bankApp.util.BankAccountUtil;
import com.alpha.bankApp.util.ResponseStructure;

/**
 * @author Manoj Y
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private BankAccountUtil bankAccountUtil;

	@Override
	public ResponseEntity<ResponseStructure<BankAccount>> getBankAccount(String bankId) {
		Optional<BankAccount> optionalBankAccount = bankAccountDao.getBankAccountByBankId(bankId);
		if (optionalBankAccount.isPresent()) {
			ResponseStructure<BankAccount> structure = new ResponseStructure<>();
			structure.setData(optionalBankAccount.get());
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setMessage("Found");
			return new ResponseEntity<ResponseStructure<BankAccount>>(structure, HttpStatus.OK);
		}
		throw new BankAccountNotFoundException("BankAccount With the Given BankId " + bankId + " Not Found");
	}

	public ResponseEntity<ResponseStructure<BankAccount>> updateBankAccountById(long bankAccountId,
			BankAccount bankAccount) {
		bankAccount = bankAccountUtil.updateBankAccount(bankAccountId, bankAccount);
		bankAccount = bankAccountDao.updateBankAccountById(bankAccountId, bankAccount);
		ResponseStructure<BankAccount> structure = new ResponseStructure<>();
		structure.setData(bankAccount);
		structure.setMessage("Modified");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<BankAccount>>(structure, HttpStatus.OK);
	}

}
