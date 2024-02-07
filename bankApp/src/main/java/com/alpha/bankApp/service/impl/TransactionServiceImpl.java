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
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.Transaction;
import com.alpha.bankApp.enums.FoundMessage;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;
import com.alpha.bankApp.exception.InsufficientBalanceException;
import com.alpha.bankApp.exception.UserAccountNotFoundException;
import com.alpha.bankApp.service.TransactionService;
import com.alpha.bankApp.util.AccountUtil;
import com.alpha.bankApp.util.ResponseStructure;
import com.alpha.bankApp.util.TransactionUtil;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountUtil accountUtil;
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private TransactionUtil transactionUtil;

	@Override
	public ResponseEntity<ResponseStructure<String>> deposit(String accountNumber, Deposit deposit) {
		Account account = accountDao.getAccountByAccountNumber(accountNumber);
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
			if (account.getAvailableBalance() == null) {
				account.setAvailableBalance(0.0);
			}
			account.setAvailableBalance(account.getAvailableBalance() + deposit.getAmount());
			deposit.setStatus(TransactionStatus.SUCESS);
			deposit.setType(TransactionType.DEPOSIT);
			deposit.setEnedDate(LocalDateTime.now());
			deposit.setStatement(statement);
			transactions.add(deposit);
			statement.setTransactions(transactions);
			account.setStatement(statement);
			accountDao.updateAccount(accountNumber, account);

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

	@Override
	public ResponseEntity<ResponseStructure<String>> fundTransfer(FundTransfer fundTransfer) {
		Account senderAccount = accountDao.getAccountByAccountNumber(fundTransfer.getSenderAccountNumber());
		if (senderAccount != null) {
			// Figuring out how much to transfer to the receiver.
			if (senderAccount.getAvailableBalance() > fundTransfer.getAmount()) {
				Account receiverAccount = accountDao.getAccountByAccountNumber(fundTransfer.getReceiverAccountNumber());
				if (receiverAccount != null) {
					// The sender's account balance will be debated.
					senderAccount.setAvailableBalance(senderAccount.getAvailableBalance() - fundTransfer.getAmount());
					fundTransfer.setFoundMessage(FoundMessage.DEBITED);
					senderAccount = transactionUtil.generateStatement(senderAccount, fundTransfer);
					accountDao.updateAccount(senderAccount.getAccountNumber(), senderAccount);

					// The Receiver account balance will be credited.
					receiverAccount
							.setAvailableBalance(receiverAccount.getAvailableBalance() + fundTransfer.getAmount());
					FundTransfer reciverTransaction = transactionUtil.generateFundTransfer(fundTransfer);
					reciverTransaction.setFoundMessage(FoundMessage.CREDITED);
					receiverAccount = transactionUtil.generateStatement(receiverAccount, reciverTransaction);
					accountDao.updateAccount(receiverAccount.getAccountNumber(), receiverAccount);
					ResponseStructure<String> structure = new ResponseStructure<>();
					structure.setData(fundTransfer.getAmount() + " Debited On " + LocalDateTime.now());
					structure.setMessage("DEBITED");
					structure.setStatusCode(HttpStatus.OK.value());
					return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
				}
				throw new UserAccountNotFoundException(
						"It appears that the account you are attempting to transfer funds to is either inactive or does not have the authority to receive the funds.");
			}
			throw new InsufficientBalanceException("Looks like the account is running on empty.");
		}
		throw new UserAccountNotFoundException(
				"It seems that the account you're trying to use is either inactive or doesn't have the authority to transfer funds.");
	}

}
