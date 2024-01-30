package com.alpha.bankApp.dao;

import java.util.List;

import com.alpha.bankApp.entity.Branch;

public interface BranchDao {
	Branch createBranch(String bankId, Branch branch);

	Branch updateBranch(String branchId, Branch branch);

	Branch getBranch(String branchId);

	Branch deleteBranch(String branchId);

	public List<Branch> getBranches();

	/**
	 * @param branchName
	 * @return
	 */
	Branch getBranchByName(String branchName);

	List<Branch> getBranchByBankId(String bankId);

	String getBranchIdByBranchManagerId(String branchManagerId);

	Branch findBranchByBranchManagerId(String employeeId);

}