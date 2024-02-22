package com.alpha.bankApp.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.alpha.bankApp.entity.Transaction;

public interface TransactionDao {

	List<Transaction> findTransactions(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);

}
