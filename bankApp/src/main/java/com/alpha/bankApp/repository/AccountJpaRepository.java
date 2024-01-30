/**
 * 
 */
package com.alpha.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Account;

/**
 * @author Dixith S N
 *
 */
public interface AccountJpaRepository extends JpaRepository<Account, String> {
	@Query("SELECT a FROM Account a WHERE a.branch.branchId=?1")
	List<Account> findAllByBranchId(String branchId);

	@Query("SELECT a.accountNumber FROM Account a ORDER BY a.creationDateTime DESC LIMIT 1")
	String findLastAccountId();

}
