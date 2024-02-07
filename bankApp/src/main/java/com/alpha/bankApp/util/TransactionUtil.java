package com.alpha.bankApp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.FundTransfer;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.Transaction;
import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;

@Component
public class TransactionUtil {

	public Account generateStatement(Account senderAccount, FundTransfer fundTransfer) {
		Statement statement = senderAccount.getStatement();
		if (statement == null) {
			statement = new Statement();
			statement.setCreatedDateAndTime(LocalDateTime.now());
			statement.setTransactions(new ArrayList<>());
		}
		List<Transaction> transactions = statement.getTransactions();
		if (transactions == null) {
			transactions = new ArrayList<>();
		}

		fundTransfer.setCreatedDate(LocalDateTime.now());
		fundTransfer.setEnedDate(LocalDateTime.now());
		fundTransfer.setStatement(statement);
		fundTransfer.setStatus(TransactionStatus.SUCESS);
		fundTransfer.setType(TransactionType.FUNDTRANSFER);

		transactions.add(fundTransfer);
		statement.setTransactions(transactions);
		senderAccount.setStatement(statement);
		return senderAccount;
	}

	public FundTransfer generateFundTransfer(FundTransfer fundTransfer) {

		return new FundTransfer(fundTransfer.getSenderAccountNumber(), fundTransfer.getReceiverAccountNumber(),
				fundTransfer.getAmount(), null);
	}

}
