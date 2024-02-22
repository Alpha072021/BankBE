package com.alpha.bankApp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BeneficiaryDao;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Beneficiary;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.exception.InvalidBeneficiaryInfoException;
import com.alpha.bankApp.exception.UserAccountNotFoundException;
import com.alpha.bankApp.exception.UserBeneficiaryNotFound;
import com.alpha.bankApp.service.BeneficiaryService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
	@Autowired
	private BeneficiaryDao beneficiaryDao;
	@Autowired
	private AccountDao accountDao;

	// making sure to record the beneficiary information provided by the customer in
	// our database.
	@Override
	public ResponseEntity<ResponseStructure<Beneficiary>> saveBeneficiary(Beneficiary beneficiary) {
		Account senderAccount = accountDao.getAccountByAccountNumber(beneficiary.getSenderAccountNumber());
		// Verifying the customer have a activated account to record Beneficiary info
		if (senderAccount != null && senderAccount.getStatus().equals(Status.ACTIVE)) {
			// Verifying the Beneficiary account exist and activated to record Beneficiary
			// info
			Account reciverAccount = accountDao.getAccountByAccountNumber(beneficiary.getReciverAccountNumber());
			if (reciverAccount != null && reciverAccount.getStatus().equals(Status.ACTIVE)) {
				// Verifying whether the beneficiary account's branch IFSC code corresponds to
				// the given code
				if (reciverAccount.getBranch().getIFSC().equals(beneficiary.getIFSCCode())) {
					beneficiary.setReciverBranchId(reciverAccount.getBranch().getBranchId());
					beneficiary.setBranchName(reciverAccount.getBranch().getBranchName());
					beneficiary.setCreatedDateTime(LocalDateTime.now());
					beneficiary = beneficiaryDao.saveBeneficiary(beneficiary);

					List<Beneficiary> beneficiarys = senderAccount.getBeneficiary();
					if (beneficiarys == null) {
						beneficiarys = new ArrayList<>();
					}
					beneficiarys.add(beneficiary);
					senderAccount.setBeneficiary(beneficiarys);
					accountDao.updateAccount(senderAccount.getAccountNumber(), senderAccount);
					ResponseStructure<Beneficiary> structure = new ResponseStructure<>();
					structure.setData(beneficiary);
					structure.setMessage("Created");
					structure.setStatusCode(HttpStatus.CREATED.value());
					return new ResponseEntity<>(structure, HttpStatus.CREATED);
				}
				throw new InvalidBeneficiaryInfoException(
						"I apologize, but the IFSC code you've entered doesn't seem to be associated with any bank.");
			}
			throw new UserAccountNotFoundException(
					"I regret to inform you that this individual Beneficiary is not an account holder.");
		}
		throw new UserAccountNotFoundException("I apologize, but it seems that your account is not yet activated.");

	}

	// providing a list of beneficiaries connected to the specified account number
	@Override
	public ResponseEntity<ResponseStructure<List<Beneficiary>>> findAllBeneficiary(String accountNumber) {
		List<Beneficiary> beneficiaries = beneficiaryDao.findAllBeneficiary(accountNumber);
		if (beneficiaries != null && !beneficiaries.isEmpty()) {
			ResponseStructure<List<Beneficiary>> structure = new ResponseStructure<>();
			structure.setData(beneficiaries);
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Beneficiary>>>(structure, HttpStatus.OK);
		}
		throw new UserBeneficiaryNotFound(
				"I regret to inform you that the account holder has not designated any beneficiaries.");
	}

	@Override
	public ResponseEntity<ResponseStructure<Beneficiary>> findBeneficiary(long beneficiaryId) {
		Optional<Beneficiary> optional = beneficiaryDao.findBeneficiary(beneficiaryId);
		if (optional.isPresent()) {
			ResponseStructure<Beneficiary> structure = new ResponseStructure<>();
			structure.setData(optional.get());
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Beneficiary>>(structure, HttpStatus.OK);
		}
		throw new UserBeneficiaryNotFound(
				"I regret to inform you that the account holder has not designated any beneficiaries With the Given BeneficiaryId.");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteBeneficiary(long beneficiaryId) {
		Optional<Beneficiary> optional = beneficiaryDao.findBeneficiary(beneficiaryId);
		if (optional.isPresent()) {
			Beneficiary beneficiary = optional.get();
			Account account = accountDao.getAccountByAccountNumber(beneficiary.getSenderAccountNumber());
			List<Beneficiary> beneficiarys = account.getBeneficiary();
			beneficiarys.remove(beneficiary);
			account.setBeneficiary(beneficiarys);
			accountDao.updateAccount(account.getAccountNumber(), account);
			beneficiaryDao.deleteBeneficiary(beneficiary);
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setData("");
			structure.setMessage("Removed");
			structure.setStatusCode(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NO_CONTENT);
		}
		throw new UserBeneficiaryNotFound(
				"I regret to inform you that the account holder has not designated any beneficiaries With the Given BeneficiaryId.");
	}

	@Override
	public ResponseEntity<ResponseStructure<Beneficiary>> updateBeneficiary(Beneficiary beneficiary) {

		Optional<Beneficiary> optional = beneficiaryDao.findBeneficiary(beneficiary.getBeneficiaryId());
		if (optional.isPresent()) {
			return saveBeneficiary(beneficiary);
		}
		throw new UserBeneficiaryNotFound(
				"I regret to inform you that the account holder has not designated any beneficiaries With the Given BeneficiaryId.");
	}

}
