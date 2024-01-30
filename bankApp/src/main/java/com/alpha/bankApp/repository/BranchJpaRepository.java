/**
 * 
 */
package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Branch;

/**
 * @author Dixith S N
 *
 */
public interface BranchJpaRepository extends JpaRepository<Branch, String> {

	Branch findByBranchName(String branchName);

	@Query(value = "SELECT b.branchId FROM Branch b ORDER BY b.creationDateAndTime DESC LIMIT 1 ")
	String getLastBranchId();

	@Query(value = "SELECT b.branchId FROM Branch b WHERE b.branchManager.employeeId=?1")
	String findBranchByManagerId(String branchManagerId);

	@Query(value = "SELECT b FROM Branch b WHERE b.branchManager.employeeId=?1")
	Branch findBranchByBranchManagerId(String employeeId);

}
