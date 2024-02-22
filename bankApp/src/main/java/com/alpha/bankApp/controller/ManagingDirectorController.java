package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.ManagingDirectorDashBoardDto;
import com.alpha.bankApp.dto.ManagingDirectorDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.ManagingDirectorService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("api/version/{version}/managingDirectors")
public class ManagingDirectorController {
	@Autowired
	private ManagingDirectorService directorService;

	@PostMapping
	public ResponseEntity<ResponseStructure<ManagingDirectorDto>> saveManagingDirector(@RequestBody Employee employee,
			@PathVariable String version, @RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1")) {
			return directorService.saveManagingDirector(employee, bankId);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@Hidden
	@PatchMapping("/setManagingDirector")
	public ResponseEntity<ResponseStructure<Employee>> setManagingDirector(@PathVariable String version,
			@RequestParam String bankId, @RequestParam String employeeId) {
		return directorService.setManagingDirector(bankId, employeeId);
	}

	@Hidden
	@PatchMapping("/changeManagingDirector")
	public ResponseEntity<ResponseStructure<Bank>> changeManagingDirector(@PathVariable String version,
			@RequestParam String bankId, @RequestParam String employeeId) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.changeManagingDirector(bankId, employeeId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseStructure<String>> removeManagingDirector(@PathVariable String version,
			@RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.removeManagingDirector(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	/*
	 * @GetMapping("/getManagingDirector") public
	 * ResponseEntity<ResponseStructure<Employee>> getManagingDirector(@PathVariable
	 * String version,
	 * 
	 * @RequestParam String bankId) { if (version.equalsIgnoreCase("v1")) return
	 * directorService.getManagingDirector(bankId); throw new
	 * VersionUnauthorizedException("Not An Authorized Version"); }
	 */
	// Not required
//	@GetMapping("/getManagingDirectorName")
//	public ResponseEntity<ResponseStructure<Employee>> getManagingDirectorName(@RequestParam String bankName) {
//		return directorService.getManagingDirectorName(bankName);
//	}
	@Hidden
	@GetMapping("/getManagingDirectorByName")
	public ResponseEntity<ResponseStructure<Employee>> getManagingDirectorByName(@PathVariable String version,
			@RequestParam String managingDirectorname) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.getManagingDirectorByName(managingDirectorname);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAllManagingDirector")
	public ResponseEntity<ResponseStructure<List<ManagingDirectorDto>>> getAllManagingDirector(
			@PathVariable String version) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.getEmployeeByRole();
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<ManagingDirectorDto>> updateManagingDirector(@PathVariable String version,
			@RequestParam String managerId, @RequestBody Employee employee) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.updateManagingDirector(managerId, employee);
		throw new VersionUnauthorizedException("Not An Authorized Version");

	}

	@DeleteMapping("/removeManagingDirector")
	public ResponseEntity<ResponseStructure<String>> removeManagingDirectorById(@PathVariable String version,
			@RequestParam String managerId) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.removeManagingDirectorById(managerId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getManagingDirector")
	public ResponseEntity<ResponseStructure<ManagingDirectorDto>> getManagingDirector(@PathVariable String version,
			@RequestHeader("Authorization") String token) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.getMD(token);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getManagingDirectorById")
	public ResponseEntity<ResponseStructure<Employee>> getManagingDirectorById(@PathVariable String version,
			@RequestParam String managingDirectorId) {
		if (version.equalsIgnoreCase("v1"))
			return directorService.getManagingDirectorById(managingDirectorId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getManagingDirectorDashBoard")
	public ResponseEntity<ResponseStructure<ManagingDirectorDashBoardDto>> getManagingDirectorDashBoard(
			@PathVariable String version, @RequestParam String managingDirectorId) {
		if (version.equals("v1"))
			return directorService.getManagingDirectorDashBoard(managingDirectorId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
