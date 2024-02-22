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

import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BeneficiaryService;
import com.alpha.bankApp.util.ResponseStructure;

@RestController
@RequestMapping("/api/version/{version}/beneficiarys")
public class BeneficiaryController {
	@Autowired
	private BeneficiaryService beneficiaryService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Beneficiary>> saveBeneficiary(@PathVariable String version,
			@RequestBody Beneficiary beneficiary) {
		if (version.equals("v1")) {
			return beneficiaryService.saveBeneficiary(beneficiary);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/findAll")
	public ResponseEntity<ResponseStructure<List<Beneficiary>>> findAllBeneficiary(@PathVariable String version,
			@RequestParam String accountNumber) {
		if (version.equals("v1")) {
			return beneficiaryService.findAllBeneficiary(accountNumber);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/findBeneficiary")
	public ResponseEntity<ResponseStructure<Beneficiary>> findBeneficiary(@PathVariable String version,
			@RequestParam long beneficiaryId) {
		if (version.equals("v1")) {
			return beneficiaryService.findBeneficiary(beneficiaryId);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@DeleteMapping
	public ResponseEntity<ResponseStructure<String>> deleteBeneficiary(@PathVariable String version,
			@RequestParam long beneficiaryId) {
		if (version.equals("v1")) {
			return beneficiaryService.deleteBeneficiary(beneficiaryId);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Beneficiary>> updateBeneficiary(@PathVariable String version,
			@RequestBody Beneficiary beneficiary) {
		if (version.equals("v1")) {
			return beneficiaryService.updateBeneficiary(beneficiary);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
