package com.alpha.bankApp.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.TransactionDao;
import com.alpha.bankApp.entity.Transaction;
import com.alpha.bankApp.repository.TransactionRepository;

@Repository
public class TransactionDaoImpl implements TransactionDao {
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Transaction> findTransactions(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
		return transactionRepository.findTransactions(accountNumber, startDate, endDate);
	}

}
