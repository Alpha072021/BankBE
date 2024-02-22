package com.alpha.bankApp.dao;

import java.util.List;

import com.alpha.bankApp.entity.Bank;

public interface BankDao {
	Bank createBank(Bank bank);

	Bank updateBank(String bankId, Bank bank);

	Bank getBank(String bankId);

	Bank deleteBank(String bankId);

	List<Bank> getBankByName(String bankName);

	int mapBranch(String bankId, String branchId);

	List<Bank> getAllBank();

	/**
	 * @param employeeId
	 * @return
	 */
	Bank getBankByMdId(String employeeId);

	List<Bank> getAllBanks();

	String findBankIdByBranchId(String branchId);

	String findBankNameByBranchId(String branchId);

}