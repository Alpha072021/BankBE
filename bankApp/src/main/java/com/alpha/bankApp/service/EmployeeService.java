package com.alpha.bankApp.service;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.util.ResponseStructure;

public interface EmployeeService {
	ResponseEntity<ResponseStructure<Employee>> saveEmployee(Employee employee);

	ResponseEntity<ResponseStructure<Employee>> updateEmployee(String employeeId, Employee employee);

	// ResponseEntity<ResponseStructure<String>> deleteEmployee(Employee employee);

	ResponseEntity<ResponseStructure<Employee>> getEmployeeById(String employeeId);

	ResponseEntity<ResponseStructure<String>> login(String email, String password);

	ResponseEntity<ResponseStructure<String>> changePassword(String employeeId, String oldPassword, String newPassword);

	ResponseEntity<ResponseStructure<Employee>> getByEmail(String email);

	ResponseEntity<ResponseStructure<Employee>> getByName(String name);

	ResponseEntity<ResponseStructure<String>> deleteById(String employeeId);

	/**
	 * @param employeeId
	 * @param name
	 * @return
	 */
	ResponseEntity<ResponseStructure<Employee>> changeName(String employeeId, String name);

	/**
	 * @param employeeId
	 * @param role
	 * @return
	 */
	ResponseEntity<ResponseStructure<Employee>> changeRole(String employeeId, Role role);
}
