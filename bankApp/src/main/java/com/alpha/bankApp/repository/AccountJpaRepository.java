/**
 * 
 */
package com.alpha.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.entity.Statement;

/**
 * @author Dixith S N
 *
 */
public interface AccountJpaRepository extends JpaRepository<Account, String> {
	@Query("SELECT a FROM Account a WHERE a.branch.branchId=?1")
	List<Account> findAllByBranchId(String branchId);

	@Query("SELECT a.accountNumber FROM Account a ORDER BY a.creationDateTime DESC LIMIT 1")
	String findLastAccountId();

	/*
	 * Introducing "findAllBeneficiaryByAccountNumber" to retrieve all beneficiaries
	 * associated with a specific account number.
	 */
	@Query("SELECT a.beneficiary FROM Account a WHERE a.accountNumber=?1")
	List<Beneficiary> findAllBeneficiaryByAccountNumber(String accountNumber);

	/*
	 * This method allows you to retrieve statements associated with an account
	 * based on the account number.
	 */
	@Query("SELECT a.statement FROM Account a WHERE a.accountNumber=?1")
	Statement findStatementByAccountId(String accountNumber);

}
