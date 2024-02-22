package com.alpha.bankApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dao.BranchManagerDao;
import com.alpha.bankApp.dto.BranchDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.exception.BankNotAssignedBranchException;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.service.BranchService;
import com.alpha.bankApp.util.BranchUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BranchServiceImpl implements BranchService {
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private BranchUtil util;
	@Autowired
	private BranchManagerDao branchManagerDao;

	@Override
	public ResponseEntity<ResponseStructure<Branch>> createBranch(String bankId, Branch branch) {
		branch = branchDao.createBranch(bankId, branch);
		if (branch != null) {
			ResponseStructure<Branch> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Created");
			responseStructure.setData(branch);
			return new ResponseEntity<ResponseStructure<Branch>>(responseStructure, HttpStatus.CREATED);
		}
		throw new BankNotFoundException("Bank with the given Id = " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Branch>> updateBranch(String branchId, Branch branch) {
		Employee employee = branchManagerDao.getBranchManager(branchId);
		branch = branchDao.updateBranch(branchId, branch);
		if (employee != null && employee.getRole().equals(Role.BRANCH_MANAGER)) {
			branch.setBranchManager(employee);
		}
		if (branch != null) {
			ResponseStructure<Branch> responseStructure = new ResponseStructure<>();
			responseStructure.setData(branch);
			responseStructure.setMessage("Modified");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Branch>>(responseStructure, HttpStatus.OK);
		}
		throw new BranchNotFoundException("Branch with the give Id = " + branchId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Branch>> getBranch(String branchId) {
		Branch branch = branchDao.getBranch(branchId);
		if (branch != null) {
			ResponseStructure<Branch> responseStructure = new ResponseStructure<>();
			responseStructure.setData(branch);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Branch>>(responseStructure, HttpStatus.OK);
		}
		throw new BranchNotFoundException("Branch with the give Id = " + branchId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteBranch(String branchId) {
		Branch branch = branchDao.getBranch(branchId);
		if (branch != null) {
			branchDao.deleteBranch(branchId);
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setData("Branch Removed");
			responseStructure.setMessage("Removed");
			responseStructure.setStatusCode(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NO_CONTENT);
		}
		throw new BranchNotFoundException("Branch with the give Id = " + branchId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<Branch>> getBranchByName(String branchName) {
		Branch branch = branchDao.getBranchByName(branchName);
		if (branch != null) {
			ResponseStructure<Branch> responseStructure = new ResponseStructure<>();
			responseStructure.setData(branch);
			responseStructure.setMessage("Found");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<Branch>>(responseStructure, HttpStatus.OK);
		}
		throw new BranchNotFoundException("Branch with the give Name = " + branchName + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Branch>>> getBranches() {

		ResponseStructure<List<Branch>> responseStructure = new ResponseStructure<>();
		responseStructure.setData(branchDao.getBranches());
		responseStructure.setMessage("Found");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Branch>>>(responseStructure, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<BranchDto>>> getAllBranchByBankId(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			List<Branch> branches = bank.getBranches();
			if (!branches.isEmpty()) {

				ResponseStructure<List<BranchDto>> responseStructure = new ResponseStructure<>();
				responseStructure.setData(util.getBranchDto(branches));
				responseStructure.setMessage("Found");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<List<BranchDto>>>(responseStructure, HttpStatus.OK);
			}
			throw new BankNotAssignedBranchException("Bank Not Have Any Branches");
		}
		throw new BankNotFoundException("Bank With the Given Id = " + bankId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<Branch>>> getAllBranchsByBankId(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			List<Branch> branches = util.getUnAssinedBranches(bank);
			if (branches != null) {
				ResponseStructure<List<Branch>> responseStructure = new ResponseStructure<>();
				responseStructure.setData(branches);
				responseStructure.setMessage("Found");
				responseStructure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<List<Branch>>>(responseStructure, HttpStatus.OK);
			}
			throw new BankNotAssignedBranchException("Branches Are Assigined With BranchManagers");
		}
		throw new BankNotFoundException("Bank With the Given Id = " + bankId + " Not Found");
	}

}
