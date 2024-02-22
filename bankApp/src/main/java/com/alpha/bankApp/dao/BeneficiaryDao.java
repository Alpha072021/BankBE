package com.alpha.bankApp.dao;

import java.util.List;
import java.util.Optional;

import com.alpha.bankApp.entity.Beneficiary;

public interface BeneficiaryDao {

	Beneficiary saveBeneficiary(Beneficiary beneficiary);

	List<Beneficiary> findAllBeneficiary(String accountNumber);

	Optional<Beneficiary> findBeneficiary(long beneficiaryId);

	void deleteBeneficiary(Beneficiary beneficiary);
}
