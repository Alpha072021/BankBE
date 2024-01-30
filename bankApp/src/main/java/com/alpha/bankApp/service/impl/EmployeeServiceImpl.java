package com.alpha.bankApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.EmployeeNotFoundException;
import com.alpha.bankApp.service.EmployeeService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public ResponseEntity<ResponseStructure<Employee>> saveEmployee(Employee employee) {
		ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
		responseStructure.setData(employeeDao.saveEmployee(employee));
		responseStructure.setMessage("Created");
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> updateEmployee(String employeeId, Employee employee) {
		employee = employeeDao.updateEmployee(employeeId, employee);
		if (employee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Modified");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getEmployeeById(String employeeId) {
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (employee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> login(String email, String password) {
		Employee employee = employeeDao.login(email, password);
		if (employee != null) {
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee.getToken());
			responseStructure.setMessage("Success");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Email" + email + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> changePassword(String employeeId, String oldPassword,
			String newPassword) {
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (employee != null) {
			employeeDao.changePassword(employeeId, oldPassword, newPassword);
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setData("Modified");
			responseStructure.setMessage("Modified");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getByEmail(String email) {
		Employee employee = employeeDao.getByEmail(email);
		if (employee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Email " + email + " Not Found");
	}

	// Need to change employee to list of employee
	@Override
	public ResponseEntity<ResponseStructure<Employee>> getByName(String name) {
		Employee employee = employeeDao.getByName(name);
		if (employee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Name " + name + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteById(String employeeId) {
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (employee != null) {
			employeeDao.deleteEmployee(employee);
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setData("Removed");
			responseStructure.setMessage("Removed");
			responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);

		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> changeName(String employeeId, String name) {
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (employee != null) {
			employeeDao.changeName(employeeId, name);
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			employee.setName(name);
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> changeRole(String employeeId, Role role) {
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (employee != null) {
			employeeDao.changeRole(employeeId, role);
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			employee.setRole(role);
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

}
