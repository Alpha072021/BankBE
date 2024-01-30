package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.BranchDto;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.util.ResponseStructure;

public interface BranchService {
	ResponseEntity<ResponseStructure<Branch>> createBranch(String bankId, Branch branch);

	ResponseEntity<ResponseStructure<Branch>> updateBranch(String branchId, Branch branch);

	ResponseEntity<ResponseStructure<Branch>> getBranch(String branchId);

	ResponseEntity<ResponseStructure<String>> deleteBranch(String branchId);

	/**
	 * @param branchName
	 * @return Response with statusCode, Message and Data as Branch Info
	 */
	ResponseEntity<ResponseStructure<Branch>> getBranchByName(String branchName);

	/**
	 * @param branchName
	 * @return Response with statusCode, Message and Data as List of Branch Info
	 *         belongs
	 */
	ResponseEntity<ResponseStructure<List<Branch>>> getBranches();

	ResponseEntity<ResponseStructure<List<BranchDto>>> getAllBranchByBankId(String bankId);

	ResponseEntity<ResponseStructure<List<Branch>>> getAllBranchsByBankId(String bankId);

}
