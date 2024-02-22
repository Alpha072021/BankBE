/**
 * 
 */
package com.alpha.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Bank;

/**
 * @author Dixith S N
 *
 */

public interface BankJpaRepository extends JpaRepository<Bank, String> {

	public List<Bank> findByBankName(String bankName);

	// To Get the Bank Details Based on the ManaginDirector Id
	@Query("select  b from Bank b where b.managingDirector.employeeId=?1")
	public Bank findByMdId(String employeeId);

	@Query("SELECT b FROM Bank b WHERE b.managingDirector=null")
	public List<Bank> getAllBanks();

	@Query("SELECT b FROM Bank b WHERE EXISTS(SELECT br FROM b.branches br WHERE br.branchId = :branchId)")
	public Bank findBankByBranchId(String branchId);

}