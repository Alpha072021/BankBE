/**
 * 
 */
package com.alpha.bankApp.dao.impl;

import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.AdminDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

/**
 * @author Dixith S N
 *
 */

@Repository
public class AdminDaoImpl extends EmployeeDaoImpl implements AdminDao  {

	@Override
	public boolean setAdmin(String employeeId) {
		
		Employee emp = getEmployeeById(employeeId) ; 
		if ( emp != null ) {
			super.changeRole(employeeId, Role.ADMIN) ; 
			return true ;
		}
		return false ; 
	}

	@Override
	public boolean removeAdmin(String employeeId) {
		// get employee by id 
		Employee emp = getEmployeeById(employeeId) ; 
		if ( emp != null && emp.getRole() == Role.ADMIN  ) {
			deleteById(employeeId);
			return true  ; 
		}
		return false;
	}

	@Override
	public Employee getAdmin(String employeeId) {
			
		Employee emp = getEmployeeById(employeeId) ; 
		
		if ( emp != null && emp.getRole() == Role.ADMIN ) {
			return emp ; 
		}
		return  null;
	}
	
}
