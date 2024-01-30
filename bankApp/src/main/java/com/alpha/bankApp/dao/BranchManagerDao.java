/**
 * 
 */
package com.alpha.bankApp.dao;

import java.util.List;

import com.alpha.bankApp.entity.Employee;

/**
 * @author Dixith S N
 *
 */
public interface BranchManagerDao extends EmployeeDao {

	boolean setBranchManager(String branchId, String employeeId);

	boolean changeBranchManager(String branchId, String employeeId);

	boolean removeBranchManager(String branchId);

	Employee getBranchManager(String branchId);

	/**
	 * @param bankId
	 * @return
	 */
	List<Employee> getBranchManagers();

	List<Employee> getBranchManagers(String bankId);

	/*
	 * Further update
	 */
//	Account approveAccount( String accountNumber , String docId ) ; 
//	
//	DebitCard approveDebitCard( String accountNumber , String docId ) ; 
}