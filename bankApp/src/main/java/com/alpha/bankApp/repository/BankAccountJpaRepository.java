/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.BankAccount;

/**
 * @author Vinod Raj
 */
public interface BankAccountJpaRepository extends JpaRepository<BankAccount, Long> {
	@Query(value = "SELECT b FROM BankAccount b WHERE bank.bankId = ?1")
	Optional<BankAccount> findByBankId(String bankId);

	@Query(value = "SELECT b.bankAccountId FROM BankAccount b WHERE bank.bankId = ?1")
	ArrayList<Long> getBankAccountIdByBankId(String bankId);


}
