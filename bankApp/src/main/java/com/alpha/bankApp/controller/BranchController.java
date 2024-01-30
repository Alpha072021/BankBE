package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.BranchDto;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BranchService;
import com.alpha.bankApp.util.BranchUtil;
import com.alpha.bankApp.util.ResponseStructure;


@RequestMapping("api/version/{version}/branchs")
@RestController
public class BranchController {
	@Autowired
	private BranchService branchService;
	@Autowired
	private BranchUtil branchUtil;

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Branch>> createBranch(@PathVariable String version,
			@RequestParam String bankId, @RequestBody Branch branch) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.createBranch(bankId, branchUtil.convertBranch(branch));
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Branch>> updateBranch(@PathVariable String version,
			@RequestParam String branchId, @RequestBody Branch branch) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.updateBranch(branchId, branchUtil.convertBranch(branch));
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getById")
	public ResponseEntity<ResponseStructure<Branch>> getBranch(@PathVariable String version,
			@RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.getBranch(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseStructure<String>> deleteBranch(@PathVariable String version,
			@RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.deleteBranch(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/branchName/{branchName}")
	public ResponseEntity<ResponseStructure<Branch>> getBranchByName(@PathVariable String version,
			@PathVariable String branchName) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.getBranchByName(branchName.toLowerCase());
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAllBranch")
	public ResponseEntity<ResponseStructure<List<BranchDto>>> getAllBranchByBankId(@PathVariable String version,
			@RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.getAllBranchByBankId(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAllUnAssigned")
	public ResponseEntity<ResponseStructure<List<Branch>>> getAllBranchsByBankId(@PathVariable String version,
			@RequestParam String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return branchService.getAllBranchsByBankId(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
