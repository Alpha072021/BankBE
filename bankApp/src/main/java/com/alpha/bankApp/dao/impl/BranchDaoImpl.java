/**
 * 
 */
package com.alpha.bankApp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.repository.BranchJpaRepository;
import com.alpha.bankApp.util.BranchUtil;

/**
 * @author Dixith S N
 *
 */
@Repository
public class BranchDaoImpl implements BranchDao {

	@Autowired
	JdbcTemplate springJdbc;

	@Autowired
	private BankDao bankDao;

	@Autowired
	private BranchJpaRepository branchRepositry;

	@Autowired
	private BranchUtil branchUtil;

	@Override
	public Branch createBranch(String bankId, Branch branch) {
		// validate Bank
		var bank = bankDao.getBank(bankId);
		if (bank != null) {

			// Generating BranchId
			branch.setBranchId(branchUtil.BranchIdGenerater(bankId));
			// Generating IFSC Code
			branch.setIFSC(branchUtil.generateIFSC(branch.getBranchId()));
			// insert branch into DB
			branch = branchRepositry.save(branch);
			// map branch to bank
			bankDao.mapBranch(bankId, branch.getBranchId());
			return branch;
		}
		return null;
	}

	@Override
	public Branch updateBranch(String branchId, Branch branch) {
		// get the branch
		var res = branchRepositry.findById(branchId);
		if (res.isPresent()) {
			branch.setBranchId(branchId);
			return branchRepositry.save(branch);
		}
		// return null if branch not updated
		return null;
	}

	@Override
	public Branch getBranch(String branchId) {
		var res = branchRepositry.findById(branchId);
		if (res.isPresent())
			return res.get();
		// return null if branch doesn't exist
		return null;
	}

	@Override
	public List<Branch> getBranches() {
		List<Branch> branches = branchRepositry.findAll();
		return branches;
	}

	@Override
	public Branch deleteBranch(String branchId) {
		// detach relationship btw Bank & Branch
		String querry = """
				delete from bank_branches
				where branches_branch_id=? ;
				""";
		int res = springJdbc.update(querry, branchId);
		if (res == 1) {
			var branch = branchRepositry.findById(branchId).get();
			branchRepositry.delete(branch);
			return branch;
		}
		return null;
	}

	@Override
	public Branch getBranchByName(String branchName) {
		return branchRepositry.findByBranchName(branchName);
	}

	@Override
	public List<Branch> getBranchByBankId(String bankId) {
		String querry = "SELECT * FROM public.branch WHERE branch_id IN (SELECT branches_branch_id FROM public.bank_branches WHERE bank_bank_id ='"
				+ bankId + "')";
		List<Branch> listOfBranches = springJdbc.query(querry, ((rs, rowNum) -> new Branch(rs.getString(1),
				rs.getString(4), rs.getString(2), null, rs.getString(3), rs.getLong(5), null)));
		return listOfBranches;
	}

	@Override
	public String getBranchIdByBranchManagerId(String branchManagerId) {
		return branchRepositry.findBranchByManagerId(branchManagerId);
	}

	@Override
	public Branch findBranchByBranchManagerId(String employeeId) {

		return branchRepositry.findBranchByBranchManagerId(employeeId);
	}

}