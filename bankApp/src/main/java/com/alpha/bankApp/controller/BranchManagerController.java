package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.alpha.bankApp.dto.BranchManagerDto;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BranchManagerService;
import com.alpha.bankApp.util.ResponseStructure;

@RestController
@RequestMapping("api/version/{version}/branchManagers")
public class BranchManagerController {
	@Autowired
	private BranchManagerService managerService;

	public ResponseEntity<ResponseStructure<Employee>> setBranchManager(@PathVariable String version,
			@RequestParam String branchId, @RequestParam String employeeId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.setBranchManager(branchId, employeeId);
		throw new VersionUnauthorizedException("Not An Authorized Version");

	}

	public ResponseEntity<ResponseStructure<Employee>> changeBranchManager(@PathVariable String version,
			@RequestParam String branchId, @RequestParam String employeeId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.changeBranchManager(branchId, employeeId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PatchMapping("/branchId")
	public ResponseEntity<ResponseStructure<String>> removeBranchManager(@PathVariable String version,
			@RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.removeBranchManager(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/branchId")
	public ResponseEntity<ResponseStructure<Employee>> getBranchManager(@PathVariable String version,
			@RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.getBranchManager(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/branchName")
	public ResponseEntity<ResponseStructure<Employee>> getBranchManagerByName(@PathVariable String version,
			@RequestParam String branchName) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.getBranchManagerByName(branchName);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Employee>>> getBranchManagers(@PathVariable String version) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.getBranchManagers();
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAll")
	public ResponseEntity<ResponseStructure<List<BranchManagerDto>>> getBranchManagers(@PathVariable String version,
			@RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.getBranchManagers(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PostMapping("/save")
	@PreAuthorize("hasRole('MANAGING_DIRECTOR')")
	public ResponseEntity<ResponseStructure<Employee>> saveBranchManager(@PathVariable String version,
			@RequestParam String branchId, @RequestBody Employee employee) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.saveBranchManager(branchId, employee);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Employee>> updateBranchManager(@PathVariable String version,
			@RequestParam String branchManagerId, @RequestBody Employee employee) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.updateBranchManager(branchManagerId, employee);
		throw new VersionUnauthorizedException("Not An Authorized Version");

	}

	@GetMapping("/getById")
	public ResponseEntity<ResponseStructure<Employee>> getBranchManagerById(@PathVariable String version,
			@RequestParam String branchManagerId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.getBranchManagerById(branchManagerId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseStructure<String>> removeBranchManagerById(@PathVariable String version,
			@RequestParam String branchManagerId) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.removeBranchManagerById(branchManagerId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getBranchManager")
	public ResponseEntity<ResponseStructure<BranchManagerDto>> getBranchManagerProfile(@PathVariable String version,
			@RequestHeader("Authorization") String token) {
		if (version.equalsIgnoreCase("v1"))
			return managerService.getBranchManagerProfile(token);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
