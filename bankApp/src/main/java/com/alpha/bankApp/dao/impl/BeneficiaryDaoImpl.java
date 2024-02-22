package com.alpha.bankApp.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BeneficiaryDao;
import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.repository.BeneficiaryRepository;

@Repository
public class BeneficiaryDaoImpl implements BeneficiaryDao {
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	@Autowired
	private AccountDao accountDao;

	@Override
	public Beneficiary saveBeneficiary(Beneficiary beneficiary) {
		return beneficiaryRepository.save(beneficiary);
	}

	@Override
	public List<Beneficiary> findAllBeneficiary(String accountNumber) {
		return accountDao.findAllBeneficiaryByAccountNumber(accountNumber);
	}

	@Override
	public Optional<Beneficiary> findBeneficiary(long beneficiaryId) {

		return beneficiaryRepository.findById(beneficiaryId);
	}

	@Override
	public void deleteBeneficiary(Beneficiary beneficiary) {
		beneficiaryRepository.delete(beneficiary);

	}

}
