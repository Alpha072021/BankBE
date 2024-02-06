/**
 * 
 */
package com.alpha.bankApp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.repository.EmployeeJpaRepository;

/**
 * @author Dixith S N
 *
 */

@Repository
@Primary
public class EmployeeDaoImpl implements EmployeeDao {
	@Autowired
	private EmployeeJpaRepository repository;

	/*
	 * @Autowired private JdbcTemplate template ;
	 */
	@Override
	public Employee login(String email, String password) {
		// TODO Auto-generated method stub
		return repository.findByEmailAndPwd(email, password);
	}

	@Override
	public void changePassword(String employeeId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub

		repository.changePassword(employeeId, oldPassword, newPassword);
	}

	public Employee getByEmail(String email) {
		return repository.findByEmail(email);
	}

	public Employee getByName(String name) {
		return repository.findByName(name);
	}

	public void deleteById(String employeeId) {
		repository.deleteById(employeeId);
	}

	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return repository.save(employee);
	}

	@Override
	public Employee updateEmployee(String employeeId, Employee employee) {
		// TODO Auto-generated method stub
		// get employee from db
		var res = repository.findById(employeeId);
		// set id for new object
		if (res.isPresent()) {
			// testing
			// System.out.println("emp is present ");
			employee.setEmployeeId(employeeId);
			employee.setPassword(res.get().getPassword());
			// merge new object
			return repository.save(employee);
		}
		return null;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		// TODO Auto-generated method stub
		repository.delete(employee);
	}

	@Override
	public Employee getEmployeeById(String employeeId) {
		// TODO Auto-generated method stub
		var res = repository.findById(employeeId);
		if (res.isPresent()) {
			return res.get();
		}
		return null;
	}

	@Override
	public int changeName(String employeeId, String name) {
		/*
		 * String query = """ update employee set name = ? where employee_id = ? """ ;
		 * template.update(query , name , employeeId ) ;
		 */
		return repository.changeName(employeeId, name);
	}

	@Override
	public int changeRole(String employeeId, Role role) {
		return repository.changeRole(employeeId, role);
	}

	// To Find the Employees Based on there role
	public List<Employee> getEmployeeByRole(Role role) {
		return repository.getEmployeeByRole(role);
	}

}