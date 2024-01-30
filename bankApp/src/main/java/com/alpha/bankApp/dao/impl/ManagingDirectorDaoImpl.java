/**
 * 
 */
package com.alpha.bankApp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.ManagingDirectorDao;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

/**
 * @author Dixith S N
 *
 */
@Repository
public class ManagingDirectorDaoImpl extends EmployeeDaoImpl implements ManagingDirectorDao {

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private BankDao bankDao;

	@Override
	public boolean setManagingDirector(String bankId, String employeeId) {
		// associate Managing Director to Bank & update the role
		String query = """
				update bank
				set managing_director_employee_id= ?
				where bank_id = ?
				""";
		int no_of_rows = template.update(query, employeeId, bankId);
		// set the role
		int res = 0;
		if (no_of_rows >= 1) {
			res = super.changeRole(employeeId, Role.MANAGING_DIRECTOR);
		}
		return no_of_rows >= 1 && res >= 1;
	}

	@Override
	public boolean removeManagingDirector(String bankId) {
		// get managingDirectorEmployeeId to change role
		var bank = bankDao.getBank(bankId);
		Employee emp = bank.getManagingDirector();
		int res = super.changeRole(emp.getEmployeeId(), Role.EMPLOYEE);
		// remove the association from bank
		bank.setManagingDirector(null);
		var res2 = bankDao.updateBank(bankId, bank);
		return res >= 1 && res2 != null;
	}

	@Override
	public boolean removeManagingDirectorById(String employeeId) {
		// get managingDirectorEmployeeId to change role
		Employee emp = getEmployeeById(employeeId);
		Bank bank = bankDao.getBankByMdId(employeeId);
		int res = 0;
		Bank res2 = null;
		if (bank != null && emp != null) {
			res = super.changeRole(emp.getEmployeeId(), Role.EMPLOYEE);

			// remove the association from bank
			bank.setManagingDirector(null);
			res2 = bankDao.updateBank(bank.getBankId(), bank);
			return res >= 1 && res2 != null;
		} else if (emp != null) {
			return super.changeRole(emp.getEmployeeId(), Role.EMPLOYEE) == 1;
		}
		return false;
	}

	@Override
	public boolean changeManagingDirector(String bankId, String employeeId) {
		// get managingDirectorEmployeeId to change role
		var bank = bankDao.getBank(bankId);
		Employee emp = bank.getManagingDirector();
		int res = super.changeRole(emp.getEmployeeId(), Role.EMPLOYEE);
		// set new ManagingDirector to the bank
		return res >= 1 && setManagingDirector(bankId, employeeId);
	}

	@Override
	public Employee getManagingDirector(String bankId) {
		return bankDao.getBank(bankId).getManagingDirector();
	}

	// Get the All MD in the Application To Display For Admin
	@Override
	public List<Employee> getAllManaginDirector() {
		return super.getEmployeeByRole(Role.MANAGING_DIRECTOR);
	}

	// To Get the Bank Details Based on the ManaginDirector Id
	@Override
	public Bank getBankByManaginDirector(String employeeId) {
		return bankDao.getBankByMdId(employeeId);
	}
}
