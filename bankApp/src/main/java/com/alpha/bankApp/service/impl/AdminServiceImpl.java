package com.alpha.bankApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.AdminDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.EmployeeNotFoundException;
import com.alpha.bankApp.exception.UnauthorizedException;
import com.alpha.bankApp.security.ApplicationUserDetailsService;
import com.alpha.bankApp.security.JWTUtils;
import com.alpha.bankApp.service.AdminService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class AdminServiceImpl extends EmployeeServiceImpl implements AdminService {
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private ApplicationUserDetailsService applicationUserDetailsService;
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public ResponseEntity<ResponseStructure<String>> setAdmin(String employeeId) {
		if (adminDao.setAdmin(employeeId)) {
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Modified");
			responseStructure.setData("Modified");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> removeAdmin(String employeeId) {
		if (adminDao.removeAdmin(employeeId)) {
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
			responseStructure.setMessage("Modified");
			responseStructure.setData("Modified");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Employee>> getAdmin(String jwtToken) {

		jwtToken = jwtToken.substring(7);
		String employeeId = (String) jwtUtils.extractAllClaims(jwtToken).get("userId");

		Employee employee = adminDao.getAdmin(employeeId);

		if (employee != null && employee.getRole().equals(Role.ADMIN)) {

			HttpHeaders headers = new HttpHeaders();
			ResponseStructure<Employee> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Found");
			responseStructure.setData(employee);
			return ResponseEntity.ok().headers(headers).body(responseStructure);
		}
		throw new EmployeeNotFoundException("Employee With the Given Id " + employeeId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> login(String email, String password) {
		Employee employee = adminDao.login(email, password);
		if (employee != null) {
			if (employee.getRole().equals(Role.ADMIN)) {
				UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(email);
				String jwtToken = jwtUtils.generateToken(userDetails, employee.getEmployeeId(), employee.getRole());
				employee.setToken(jwtToken);
				adminDao.saveEmployee(employee);
				ResponseStructure<String> responseStructure = new ResponseStructure<>();
				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("Success");
				responseStructure.setData(employee.getToken());
				return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
			}
			throw new UnauthorizedException("Employee With the Given Detalies Not An Admin");
		}
		throw new EmployeeNotFoundException("Employee With the Given Detalies Not Found");
	}

}
