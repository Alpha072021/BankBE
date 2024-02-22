package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.util.ResponseStructure;

public interface BeneficiaryService {

	ResponseEntity<ResponseStructure<Beneficiary>> saveBeneficiary(Beneficiary beneficiary);

	ResponseEntity<ResponseStructure<List<Beneficiary>>> findAllBeneficiary(String accountNumber);

	ResponseEntity<ResponseStructure<Beneficiary>> findBeneficiary(long beneficiaryId);

	ResponseEntity<ResponseStructure<String>> deleteBeneficiary(long beneficiaryId);

	ResponseEntity<ResponseStructure<Beneficiary>> updateBeneficiary(Beneficiary beneficiary);
}
