package com.alpha.bankApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.dao.ManagingDirectorDao;
import com.alpha.bankApp.dto.ManagingDirectorDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.BankNotAssignedMDException;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.EmployeeNotAssingedRoleException;
import com.alpha.bankApp.exception.EmployeeNotFoundException;
import com.alpha.bankApp.security.JWTUtils;
import com.alpha.bankApp.service.ManagingDirectorService;
import com.alpha.bankApp.util.EmployeeUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class ManagingDirectorServiceImpl extends EmployeeServiceImpl implements ManagingDirectorService {
	@Autowired
	private BankDao bankDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private ManagingDirectorDao directorDao;
	@Autowired
	private EmployeeUtil util;
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public ResponseEntity<ResponseStructure<Employee>> setManagingDirector(String bankId, String employeeId) {
		Bank bank = bankDao.getBank(bankId);
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (bank != null) {
			if (employee != null) {
				directorDao.setManagingDirector(bankId, employeeId);
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(employee);
				responseStructure.setMessage("Modified");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotFoundException("Employee with the Id" + employeeId + " not Found");
		}
		throw new BankNotFoundException("Bank with the Given Id " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Bank>> changeManagingDirector(String bankId, String employeeId) {
		Bank bank = bankDao.getBank(bankId);
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (bank != null) {
			if (employee != null) {
				directorDao.changeManagingDirector(bankId, employeeId);
				ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
				responseStructure.setData(bank);
				responseStructure.setMessage("Modified");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Bank>>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotFoundException("Employee with the Id" + employeeId + " not Found");
		}
		throw new BankNotFoundException("Bank with the Given Id " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> removeManagingDirector(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			if (directorDao.removeManagingDirector(bankId)) {
				ResponseStructure<String> responseStructure = new ResponseStructure<>();
				responseStructure.setData("Modified");
				responseStructure.setMessage("Modified");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
			}
			throw new BankNotAssignedMDException("Bank Not Assigned An ManagingDirector");
		}
		throw new BankNotFoundException("Bank with the Id " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getManagingDirector(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			if (bank.getManagingDirector() != null) {
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(bank.getManagingDirector());
				responseStructure.setMessage("Found");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
			}
			throw new BankNotAssignedMDException("Bank Not Assigned An ManagingDirector");
		}
		throw new BankNotFoundException("Bank with the Id " + bankId + " Not Found");
	}

//	@Override
//	public ResponseEntity<ResponseStructure<Employee>> getManagingDirectorName(String bankName) {
//		Bank bank = bankDao.getBankByName(bankName);
//		if (bank != null) {
//			if (bank.getManagingDirector() != null) {
//				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
//				responseStructure.setData(bank.getManagingDirector());
//				responseStructure.setMessage("Found");
//				responseStructure.setStatusCode(HttpStatus.OK.value());
//				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
//			}
//			throw new BankNotAssignedMDException("Bank Not Assigned An ManagingDirector");
//		}
//		throw new BankNotFoundException("Bank with the Name " + bankName + " Not Found");
//	}

//We may endup with a null pointer exception
	@Override
	public ResponseEntity<ResponseStructure<Employee>> getManagingDirectorByName(String managingDirectorname) {
		Employee employee = directorDao.getManagingDirector(managingDirectorname);
		if (employee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new BankNotAssignedMDException("Bank Not Assigned An ManagingDirector");
	}

	@Override
	public ResponseEntity<ResponseStructure<ManagingDirectorDto>> saveManagingDirector(Employee employee,
			String bankId) {

		Bank bank = bankDao.getBank(bankId);
		Employee updateEmployee = null;
		if (bank != null) {
			employee = util.convertEmployeeInfo(employee);
			employee.setRole(Role.MANAGING_DIRECTOR);
			updateEmployee = employeeDao.saveEmployee(employee);
			boolean flag = directorDao.setManagingDirector(bankId, updateEmployee.getEmployeeId());
			ManagingDirectorDto managinDirector = util.createManaginDirector(updateEmployee, bank);
			if (flag) {
				ResponseStructure<ManagingDirectorDto> responseStructure = new ResponseStructure<>();
				responseStructure.setData(managinDirector);
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Employee Created As MD and Assigned to an Bank");
				return new ResponseEntity<ResponseStructure<ManagingDirectorDto>>(responseStructure,
						HttpStatus.CREATED);
			}
			throw new BankNotAssignedMDException("Bank Not Assigned An ManagingDirector");
		}
		throw new BankNotFoundException("Bank with the Given Id " + bankId + " Not Found");

	}

	@Override
	public ResponseEntity<ResponseStructure<List<ManagingDirectorDto>>> getEmployeeByRole() {
		List<Employee> list = directorDao.getAllManaginDirector();

		if (list != null && !(list.isEmpty())) {
			ResponseStructure<List<ManagingDirectorDto>> responseStructure = new ResponseStructure<>();
			responseStructure.setData(util.createManaginDirector(list));
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<ManagingDirectorDto>>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotAssingedRoleException("No ManaginDirector Exists");
	}

	@Override
	public ResponseEntity<ResponseStructure<ManagingDirectorDto>> updateManagingDirector(String managerId,
			Employee employee) {
//		return super.updateEmployee(managerId, employee);
		employee = employeeDao.updateEmployee(managerId, employee);
		// To Return BankInfo to the
		Bank bank = bankDao.getBankByMdId(managerId);
		if (employee != null) {
			ResponseStructure<ManagingDirectorDto> structure = new ResponseStructure<>();
			structure.setMessage("Modified");
			structure.setStatusCode(HttpStatus.OK.value());
			structure.setData(util.createManaginDirector(employee, bank));
			return new ResponseEntity<ResponseStructure<ManagingDirectorDto>>(structure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + managerId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> removeManagingDirectorById(String managerId) {
		if (directorDao.removeManagingDirectorById(managerId)) {
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setData("Removed");
			responseStructure.setMessage("Removed");
			responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
		}
		throw new EmployeeNotAssingedRoleException("No ManaginDirector Exists");
	}

	@Override
	public ResponseEntity<ResponseStructure<ManagingDirectorDto>> getMD(String token) {
		// Get MD Based on the Token
		token = token.substring(7);
		String employeeId = (String) jwtUtils.extractAllClaims(token).get("userId");

		Employee employee = directorDao.getEmployeeById(employeeId);
		if (employee != null) {
			if (employee.getRole().equals(Role.MANAGING_DIRECTOR)) {
				ManagingDirectorDto managinDirector = null;
				Bank bank = directorDao.getBankByManaginDirector(employeeId);
				if (bank != null)
					managinDirector = util.createManaginDirector(employee, bank);
				else
					managinDirector = util.createManaginDirector(employee);
				ResponseStructure<ManagingDirectorDto> responseStructure = new ResponseStructure<>();
				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("Found");
				responseStructure.setData(managinDirector);
				return new ResponseEntity<>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotAssingedRoleException(
					"The position of Managing Director is currently vacant or unassigned within the employee");
		}
		throw new EmployeeNotFoundException("ManagingDirector With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getManagingDirectorById(String managingDirectorId) {

		Employee employee = employeeDao.getEmployeeById(managingDirectorId);
		if (employee != null) {
			if (employee.getRole().equals(Role.MANAGING_DIRECTOR)) {
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(employee);
				responseStructure.setMessage("Found");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotAssingedRoleException(
					"The position of Managing Director is currently vacant or unassigned within the employee");
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + managingDirectorId + " Not Found");
	}

}
