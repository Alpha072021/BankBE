package com.alpha.bankApp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.Deposit;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.Transaction;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;
import com.alpha.bankApp.exception.UserAccountNotFoundException;
import com.alpha.bankApp.service.TransactionService;
import com.alpha.bankApp.util.AccountUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class DepositServiceImpl implements TransactionService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountUtil accountUtil;
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private BankDao bankDao;

	@Override
	public ResponseEntity<ResponseStructure<String>> deposit(String accountNumner, Deposit deposit) {
		Account account = accountDao.getAccountByAccountNumber(accountNumner);
		if (account != null && account.getBranch() != null && account.getStatus().equals(Status.ACTIVE)) {
			Statement statement = account.getStatement();
			if (statement == null) {
				statement = accountUtil.createStatement();
			}
			List<Transaction> transactions = statement.getTransactions();
			if (transactions == null) {
				transactions = new ArrayList<>();
			}
			// Deposit to the user account
			account.setAvailableBalance(account.getAvailableBalance() + deposit.getAmount());
			deposit.setStatus(TransactionStatus.SUCESS);
			deposit.setType(TransactionType.DEPOSIT);
			deposit.setEnedDate(LocalDateTime.now());
			transactions.add(deposit);
			statement.setTransactions(transactions);
			account.setStatement(statement);
			accountDao.updateAccount(accountNumner, account);

			// Deposit to the bankAccount
			Optional<BankAccount> optional = bankAccountDao
					.getBankAccountByBankId((bankDao.findBankIdByBranchId(account.getBranch().getBranchId())));
			BankAccount bankAccount = optional.get();
			bankAccount.setBankBalance(bankAccount.getBankBalance() + deposit.getAmount());
			bankAccountDao.updateBankAccountById(bankAccount.getBankAccountId(), bankAccount);
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setData("Deposit Success");
			structure.setMessage("Success");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);

		}
		throw new UserAccountNotFoundException("Individual without an account or inactive user account.");
	}

}
