package com.alpha.bankApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dao.BranchManagerDao;
import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.dto.BranchManagerDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.BranchNotAssignedException;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.exception.EmployeeNotAssingedRoleException;
import com.alpha.bankApp.exception.EmployeeNotFoundException;
import com.alpha.bankApp.exception.UnauthorizedException;
import com.alpha.bankApp.security.JWTUtils;
import com.alpha.bankApp.service.BranchManagerService;
import com.alpha.bankApp.util.EmployeeUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BranchManagerServiceImpl extends EmployeeServiceImpl implements BranchManagerService {
	@Autowired
	private BranchManagerDao branchManagerDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private EmployeeUtil util;
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public ResponseEntity<ResponseStructure<Employee>> setBranchManager(String branchId, String employeeId) {
		Branch branch = branchDao.getBranch(branchId);
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (branch != null) {
			if (employee != null) {
				branchManagerDao.setBranchManager(branchId, employeeId);
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(employee);
				responseStructure.setMessage("Modified");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotFoundException("Emplouyee with the Given Id " + employeeId + " Not Found");
		}
		throw new BranchNotFoundException("Branch With the Given Id " + branchId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> changeBranchManager(String branchId, String employeeId) {
		Branch branch = branchDao.getBranch(branchId);
		Employee employee = employeeDao.getEmployeeById(employeeId);
		if (branch != null) {
			if (employee != null) {
				branchManagerDao.changeBranchManager(branchId, employeeId);
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(employee);
				responseStructure.setMessage("Modified");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotFoundException("Emplouyee with the Given Id " + employeeId + " Not Found");
		}
		throw new BranchNotFoundException("Branch With the Given Id " + branchId + " Not Found");
	}

	// If Branch does'not have any BM it may makes sql exception
	@Override
	public ResponseEntity<ResponseStructure<String>> removeBranchManager(String branchId) {

		if (branchManagerDao.removeBranchManager(branchId)) {
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setData("Modified");
			responseStructure.setMessage("Modified");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		}
		throw new BranchNotFoundException("Branch with the Given Id " + branchId + " Not Found");
	}

	// We may endup with NullPointerException Here
	@Override
	public ResponseEntity<ResponseStructure<Employee>> getBranchManager(String branchId) {
		Employee employee = branchManagerDao.getBranchManager(branchId);
		if (employee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Branch With the Given Id " + branchId + " Not Assigned Branch Manager");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getBranchManagerByName(String branchName) {
		Branch branch = branchDao.getBranchByName(branchName);
		if (branch != null) {
			Employee employee = branch.getBranchManager();
			if (employee != null) {
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(employee);
				responseStructure.setMessage("Found");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
			}
			throw new EmployeeNotFoundException("BranchManager Not Assiged For Branch " + branchName);

		}
		throw new BranchNotFoundException("Branch With the Give Name " + branchName + " Not Found");
	}

	// We should except the bankId and send the List Of BranchMaanagers
	@Override
	public ResponseEntity<ResponseStructure<List<Employee>>> getBranchManagers() {
		ResponseStructure<List<Employee>> responseStructure = new ResponseStructure<>();
		responseStructure.setData(branchManagerDao.getBranchManagers());
		responseStructure.setMessage("Found");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Employee>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<BranchManagerDto>>> getBranchManagers(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			ResponseStructure<List<BranchManagerDto>> responseStructure = new ResponseStructure<>();
			responseStructure.setData(util.getBranchManagers(bank));
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<BranchManagerDto>>>(responseStructure, HttpStatus.OK);
		}
		throw new BankNotFoundException("Bank With the Given Id" + bankId + " Not Found");
	}

	// To save Branch Manager By taking Employee object and the BranchId
	@Override
	public ResponseEntity<ResponseStructure<Employee>> saveBranchManager(String branchId, Employee employee) {
		Branch branch = branchDao.getBranch(branchId);
		if (branch != null) {
			employee = util.convertEmployeeInfo(employee);
			employee.setRole(Role.BRANCH_MANAGER);
			employee = branchManagerDao.saveEmployee(employee);
			if (branchManagerDao.setBranchManager(branchId, employee.getEmployeeId())) {
				ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
				responseStructure.setData(employee);
				responseStructure.setMessage("Created");
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.CREATED);
			}
			throw new EmployeeNotAssingedRoleException("Branch Not Assigned An BranchManager");
		}

		throw new BranchNotFoundException("Branch With the Given Id " + branchId + " Not Found");
	}

	// To update Branch Manager By taking Employee object and the BranchManagerId
	@Override
	public ResponseEntity<ResponseStructure<Employee>> updateBranchManager(String branchManagerId, Employee employee) {
		Employee updatedemployee = branchManagerDao.updateEmployee(branchManagerId, employee);
		if (updatedemployee != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(employee);
			responseStructure.setMessage("Modified");
			responseStructure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.FOUND);
		}
		throw new EmployeeNotFoundException("Branch Manager With The Given Id +" + branchManagerId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getBranchManagerById(String branchManagerId) {
		Employee branchManager = branchManagerDao.getEmployeeById(branchManagerId);
		if (branchManager != null) {
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setData(branchManager);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Employee>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + branchManagerId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> removeBranchManagerById(String branchManagerId) {
		Employee branchManager = branchManagerDao.getEmployeeById(branchManagerId);
		if (branchManager != null) {
			if (branchManager.getRole().equals(Role.BRANCH_MANAGER)) {
				String branchId = branchDao.getBranchIdByBranchManagerId(branchManagerId);
				if (branchManagerDao.removeBranchManager(branchId)) {
					ResponseStructure<String> responseStructure = new ResponseStructure<>();
					responseStructure.setData("BranchManager Role Removed");
					responseStructure.setMessage("Removed");
					responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
					return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
				}
				throw new EmployeeNotAssingedRoleException(
						"Employee with the Given Id " + branchManagerId + " is Not a BranchManager++");
			}
			throw new EmployeeNotAssingedRoleException(
					"Employee with the Given Id " + branchManagerId + " is Not a BranchManager---");
		}
		throw new EmployeeNotFoundException("BranchManager With the Given Id" + branchManagerId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<BranchManagerDto>> getBranchManagerProfile(String token) {
		token = token.substring(7);
		String employeeId = (String) jwtUtils.extractAllClaims(token).get("userId");
		Employee employee = branchManagerDao.getEmployeeById(employeeId);
		if (employee != null) {
			Branch branch = branchDao.findBranchByBranchManagerId(employee.getEmployeeId());
			if (branch != null) {
				ResponseStructure<BranchManagerDto> structure = new ResponseStructure<>();
				structure.setData(util.getBranchManagerDto(branch));
				structure.setMessage("Found");
				structure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<BranchManagerDto>>(structure, HttpStatus.OK);
			}
			throw new BranchNotAssignedException("Employee UnAssigned As BranchManager");
		}
		throw new UnauthorizedException("Invalied Token");
	}

}
