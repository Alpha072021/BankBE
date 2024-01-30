/**
 * 
 */
package com.alpha.bankApp.dao;

import com.alpha.bankApp.entity.Employee;

/**
 * @author Dixith S N
 *
 */

public interface AdminDao extends EmployeeDao {

	boolean setAdmin(String employeeId);

	boolean removeAdmin(String employeeId);

	Employee getAdmin(String employeeId);
}
