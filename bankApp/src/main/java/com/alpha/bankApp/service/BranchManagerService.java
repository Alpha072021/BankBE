package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.BranchManagerDto;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.util.ResponseStructure;

public interface BranchManagerService extends EmployeeService {
	ResponseEntity<ResponseStructure<Employee>> setBranchManager(String branchId, String employeeId);

	ResponseEntity<ResponseStructure<Employee>> changeBranchManager(String branchId, String employeeId);

	ResponseEntity<ResponseStructure<String>> removeBranchManager(String branchId);

	ResponseEntity<ResponseStructure<Employee>> getBranchManager(String branchId);

	ResponseEntity<ResponseStructure<Employee>> getBranchManagerByName(String branchName);

	/**
	 * @param bankId
	 * @return
	 */
	ResponseEntity<ResponseStructure<List<Employee>>> getBranchManagers();

	ResponseEntity<ResponseStructure<List<BranchManagerDto>>> getBranchManagers(String bankId);

	// To Save the BranchManager By ManagingDirector
	ResponseEntity<ResponseStructure<Employee>> saveBranchManager(String branchId, Employee employee);

	// To Update the BranchManager Details
	ResponseEntity<ResponseStructure<Employee>> updateBranchManager(String branchManagerId, Employee employee);

	ResponseEntity<ResponseStructure<Employee>> getBranchManagerById(String branchManagerId);

	ResponseEntity<ResponseStructure<String>> removeBranchManagerById(String branchManagerId);

	ResponseEntity<ResponseStructure<BranchManagerDto>> getBranchManagerProfile(String token);

	/*
	 * Further update
	 */
//	Account approveAccount( String accountNumber , String docId ) ; 
//	
//	DebitCard approveDebitCard( String accountNumber , String docId ) ; 
}
