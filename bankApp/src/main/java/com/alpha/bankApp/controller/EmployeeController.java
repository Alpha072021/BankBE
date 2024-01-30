package com.alpha.bankApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.EmployeeService;
import com.alpha.bankApp.util.EmployeeUtil;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("api/version/{version}/employees")
public class EmployeeController {
	@Autowired
	@Qualifier(value = "employeeServiceImpl")
	private EmployeeService employeeService;

	@Autowired
	private EmployeeUtil util;

	@PostMapping
	public ResponseEntity<ResponseStructure<Employee>> saveEmployee(@PathVariable String version,
			@RequestBody Employee employee) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.saveEmployee(util.convertEmployeeInfo(employee));
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping("/employeeId")
	public ResponseEntity<ResponseStructure<Employee>> updateEmployee(@PathVariable String version,
			@RequestParam String employeeId, @RequestBody Employee employee) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.updateEmployee(employeeId, util.convertEmployeeInfo(employee));
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/employeeId")
	public ResponseEntity<ResponseStructure<Employee>> getEmployeeById(@PathVariable String version,
			@RequestParam String employeeId) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.getEmployeeById(employeeId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@Hidden
	@PostMapping("/login")
	ResponseEntity<ResponseStructure<String>> login(@PathVariable String version, @RequestParam String email,
			@RequestParam String password) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.login(email.toLowerCase(), password);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PatchMapping("/changePassword")
	public ResponseEntity<ResponseStructure<String>> changePassword(@PathVariable String version,
			@RequestParam String employeeId, @RequestParam String oldPassword, @RequestParam String newPassword) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.changePassword(employeeId, oldPassword, newPassword);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<ResponseStructure<Employee>> getByEmail(@PathVariable String version,
			@PathVariable String email) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.getByEmail(email.toLowerCase());
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<ResponseStructure<Employee>> getByName(@PathVariable String version,
			@PathVariable String name) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.getByName(name.toLowerCase());
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping("/employeeId/{employeeId}")
	public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable String version,
			@PathVariable String employeeId) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.deleteById(employeeId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PatchMapping("/changeName")
	public ResponseEntity<ResponseStructure<Employee>> changeName(@PathVariable String version,
			@RequestParam String employeeId, @RequestParam String name) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.changeName(employeeId, name.toLowerCase());
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PatchMapping("/changeRole")
	public ResponseEntity<ResponseStructure<Employee>> changeRole(@PathVariable String version,
			@RequestParam String employeeId, @RequestParam Role role) {
		if (version.equalsIgnoreCase("v1"))
			return employeeService.changeRole(employeeId, role);
		throw new VersionUnauthorizedException("Not An Authorized Version");

	}
}
