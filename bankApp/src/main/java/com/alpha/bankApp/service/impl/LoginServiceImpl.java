package com.alpha.bankApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.dto.LoginDto;
import com.alpha.bankApp.dto.LoginResponseDto;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.EmployeeNotFoundException;
import com.alpha.bankApp.exception.UnauthorizedException;
import com.alpha.bankApp.security.ApplicationUserDetailsService;
import com.alpha.bankApp.security.JWTUtils;
import com.alpha.bankApp.service.LoginService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private ApplicationUserDetailsService applicationUserDetailsService;
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public ResponseEntity<ResponseStructure<LoginResponseDto>> login(LoginDto loginInfo, String users) {
		/*
		 * if (users.equalsIgnoreCase("customer")) { Employee employee =
		 * adminDao.login(email.toLowerCase(), password);
		 */
		if (users.equalsIgnoreCase("employee")) {
			Employee employee = employeeDao.login(loginInfo.getEmail(), loginInfo.getPassword());
			if (employee != null) {
				if (employee.getRole().equals(Role.ADMIN)) {
					return validate(employee);
				} else if (employee.getRole().equals(Role.MANAGING_DIRECTOR)) {
					return validate(employee);
				} else if (employee.getRole().equals(Role.BRANCH_MANAGER)) {
					return validate(employee);
				} // If the role is employee will be validated
				/*
				 * else if (employee.getRole().equals(Role.EMPLOYEE)) return validate(employee);
				 */
				throw new UnauthorizedException("Employee With the Given Detalies Role UnAssigned");
			}
			throw new EmployeeNotFoundException("Employee With the Given Detalies Not Found");
		} else if (users.equalsIgnoreCase("customer")) {
			return null;
		}
		throw new UnauthorizedException("Provide Proper User Info");

	}

	public ResponseEntity<ResponseStructure<LoginResponseDto>> validate(Employee employee) {
		UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(employee.getEmail());
		String jwtToken = jwtUtils.generateToken(userDetails, employee.getEmployeeId(), employee.getRole());
		employee.setToken(jwtToken);
		employeeDao.saveEmployee(employee);

		// Seting Hedders
		// "Origin", "Content-Type", "Accept", "Authorization"
		HttpHeaders headers = new HttpHeaders();
		headers.set("exposedHeaders", "Access-Control-Allow-Origin");
		headers.set("exposedHeaders1", "Access-Control-Allow-Credentials");

		ResponseStructure<LoginResponseDto> responseStructure = new ResponseStructure<>();
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setRole(employee.getRole());
		loginResponseDto.setToken(employee.getToken());
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Success");
		responseStructure.setData(loginResponseDto);

		return ResponseEntity.ok().headers(headers).body(responseStructure);
	}

}
