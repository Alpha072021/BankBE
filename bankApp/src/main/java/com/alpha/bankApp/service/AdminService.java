package com.alpha.bankApp.service;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.util.ResponseStructure;

public interface AdminService extends EmployeeService {
	ResponseEntity<ResponseStructure<String>> setAdmin(String employeeId);

	ResponseEntity<ResponseStructure<String>> removeAdmin(String employeeId);

	ResponseEntity<ResponseStructure<Employee>> getAdmin(String token);

}
