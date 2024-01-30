/**
 * 
 */
package com.alpha.bankApp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dao.BranchManagerDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

/**
 * @author Dixith S N
 *
 */
@Repository
public class BranchManagerDaoImpl extends EmployeeDaoImpl implements BranchManagerDao {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private BranchDao branchDao;

	@Override
	public boolean setBranchManager(String branchId, String employeeId) {
		// TODO Auto-generated method stub
		// set association with branch
		String querry = """
				update branch
				set branch_manager_employee_id = ?
				where branch_id = ?
				""";
		int no_of_rows = template.update(querry, employeeId, branchId);
		// update the role
		if (no_of_rows >= 1) {
			super.changeRole(employeeId, Role.BRANCH_MANAGER);
		}
		return no_of_rows >= 1;
	}

	@Override
	public boolean removeBranchManager(String branchId) {
		// get branchManagerEmployeeId to change role
		var branch = branchDao.getBranch(branchId);
		Employee emp = branch.getBranchManager();
		// change role to Employee
		super.changeRole(emp.getEmployeeId(), Role.EMPLOYEE);
		// remove the association from branch
		branch.setBranchManager(null);
		var res = branchDao.updateBranch(branchId, branch);
		return res != null;
	}

	@Override
	public boolean changeBranchManager(String branchId, String employeeId) {
		// get old branchManagerEmployeeId to change role
		var branch = branchDao.getBranch(branchId);
		Employee emp = branch.getBranchManager();
		super.changeRole(emp.getEmployeeId(), Role.EMPLOYEE);
		// set new branchManager
		return setBranchManager(branchId, employeeId);
	}

	@Override
	public Employee getBranchManager(String branchId) {
		// TODO Auto-generated method stub
		return branchDao.getBranch(branchId).getBranchManager();
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Employee> getBranchManagers() {
		String query = """
				select * from employee
				where role = ? ;
				""";
		List<Employee> managers = null;
		try {
			managers = template.query(query, new Object[] { Role.BRANCH_MANAGER.ordinal() },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Throwable e) {
			System.out.println(e);
		}
		return managers;
	}

//Not At Implemented 
	@Override
	public List<Employee> getBranchManagers(String bankId) {
		return null;
	}

}