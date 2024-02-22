package com.alpha.bankApp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query("SELECT t FROM Transaction t INNER JOIN Statement s ON t.statement.statementId = s.statementId INNER JOIN Account a ON s.statementId = a.statement.statementId WHERE a.accountNumber = ?1 AND t.createdDate BETWEEN ?2 AND ?3")
	List<Transaction> findTransactions(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);

}
