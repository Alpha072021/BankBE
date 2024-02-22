package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.ManagingDirectorDashBoardDto;
import com.alpha.bankApp.dto.ManagingDirectorDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.util.ResponseStructure;

public interface ManagingDirectorService extends EmployeeService {
	ResponseEntity<ResponseStructure<Employee>> setManagingDirector(String bankId, String employeeId);

	ResponseEntity<ResponseStructure<Bank>> changeManagingDirector(String bankId, String employeeId);

	ResponseEntity<ResponseStructure<String>> removeManagingDirector(String bankId);

	ResponseEntity<ResponseStructure<Employee>> getManagingDirector(String bankId);

	// ResponseEntity<ResponseStructure<Employee>> getManagingDirectorName(String
	// bankName);

	ResponseEntity<ResponseStructure<Employee>> getManagingDirectorByName(String managingDirectorname);

	ResponseEntity<ResponseStructure<ManagingDirectorDto>> saveManagingDirector(Employee employee, String bankId);

	ResponseEntity<ResponseStructure<List<ManagingDirectorDto>>> getEmployeeByRole();

	ResponseEntity<ResponseStructure<ManagingDirectorDto>> updateManagingDirector(String managerId, Employee employee);

	ResponseEntity<ResponseStructure<String>> removeManagingDirectorById(String managerId);

	ResponseEntity<ResponseStructure<ManagingDirectorDto>> getMD(String token);

	ResponseEntity<ResponseStructure<Employee>> getManagingDirectorById(String managingDirectorId);

	ResponseEntity<ResponseStructure<ManagingDirectorDashBoardDto>> getManagingDirectorDashBoard(
			String managingDirectorId);

}
