/**
 * 
 */
package com.alpha.bankApp.dao;

import java.util.List;

import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Employee;

/**
 * @author Dixith S N
 *
 */
public interface ManagingDirectorDao extends EmployeeDao {

	boolean setManagingDirector(String bankId, String employeeId);

	boolean changeManagingDirector(String bankId, String employeeId);

	boolean removeManagingDirector(String bankId);

	Employee getManagingDirector(String bankId);

	List<Employee> getAllManaginDirector();

	Bank getBankByManaginDirector(String employeeId);

	/**
	 * @param employeeId
	 * @return
	 */
	boolean removeManagingDirectorById(String employeeId);

}
