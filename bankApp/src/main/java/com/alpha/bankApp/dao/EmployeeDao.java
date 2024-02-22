/**
 * 
 */
package com.alpha.bankApp.dao;

import java.util.List;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

/**
 * @author Dixith S N
 *
 */
public interface EmployeeDao {
	
	Employee saveEmployee( Employee employee); 
	
	Employee updateEmployee(String employeeId, Employee employee); 
	
	void deleteEmployee(Employee employee) ; 
	
	Employee getEmployeeById(String employeeId ) ; 

	Employee login(String email , String password) ; 
	
	void changePassword(String employeeId, String oldPassword, String newPassword) ;
	
	public Employee getByEmail(String email) ; 
	
	public Employee getByName(String name ) ; 
	
	void deleteById(String employeeId ) ;

	/**
	 * @param employeeId
	 * @param name
	 * @return 
	 */
	int changeName(String employeeId, String name);

	/**
	 * @param employeeId
	 * @param role
	 * @return
	 */
	int changeRole(String employeeId, Role role);


	/**
	 * @param Role
	 * @return List of Employee
	 */
	List<Employee> getEmployeeByRole(Role role);

	/*
	 * This method is specifically designed to retrieve Employee profiles based on their
	 * employee IDs.
	 */
	String findEmployeeProfileById(String id);
}