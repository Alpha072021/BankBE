package com.alpha.bankApp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dto.BranchDto;
import com.alpha.bankApp.dto.BranchManagerDashBoardDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.idgenerator.AddressIdGenerator;
import com.alpha.bankApp.enums.AccountType;
import com.alpha.bankApp.repository.BranchJpaRepository;

@Component
public class BranchUtil {
	@Autowired
	private AddressIdGenerator generator;
	@Autowired
	private BranchJpaRepository repository;

	public Branch convertBranch(Branch branch) {
		if (branch.getBranchName() != null)
			branch.setBranchName(branch.getBranchName().toLowerCase());
		if (branch.getBranchEmail() != null)
			branch.setBranchEmail(branch.getBranchEmail().toLowerCase());
		if (branch.getAddress() != null) {
			branch.getAddress().setAddressId(generator.generate());
		}
		if (branch.getCreationDateAndTime() == null) {
			branch.setCreationDateAndTime(LocalDateTime.now());
		}
		return branch;
	}

	public String BranchIdGenerater(String bankId) {
		String lastBranchId = repository.getLastBranchId();

		int suffix = 0;
		if (lastBranchId != null) {
			suffix = Integer.parseInt(lastBranchId.substring(lastBranchId.length() - 3, lastBranchId.length()));
		}
		String prefix = bankId.substring(0, 4);
		if (suffix < 9) {
			prefix += "00" + ++suffix;
		} else if (suffix < 99) {
			prefix += "0" + ++suffix;
		} else {
			prefix += ++suffix;
		}

		return prefix;
	}

	public String generateIFSC(String branchId) {
		return branchId.substring(0, 5) + "000" + branchId.substring(4);

	}

	public List<Branch> getUnAssinedBranches(Bank bank) {

		List<Branch> branchs = bank.getBranches();
		if (branchs != null) {
			branchs = branchs.stream().filter(branch -> (branch.getBranchManager() == null))
					.collect(Collectors.toList());
			return branchs;
		}
		return null;
	}

	public BranchDto getBranchDto(Branch branch) {
		if (branch.getBranchManager() != null) {
			return new BranchDto(branch.getBranchId(), branch.getBranchName(), branch.getAddress(), branch.getIFSC(),
					branch.getBranchManager().getName());
		}
		return new BranchDto(branch.getBranchId(), branch.getBranchName(), branch.getAddress(), branch.getIFSC(),
				"UnAssigned");
	}

	public List<BranchDto> getBranchDto(List<Branch> branchs) {
		List<BranchDto> branchDtos = new ArrayList<>();
		for (Branch branch : branchs) {
			branchDtos.add(getBranchDto(branch));
		}
		return branchDtos;
	}

	public BranchManagerDashBoardDto generateBranchManagerDashBoard(Branch branch) {
		BranchManagerDashBoardDto branchManagerDashBoard = new BranchManagerDashBoardDto();
		long totalSavingAccount = getCountOfSavingAccount(branch.getAccounts());
		branchManagerDashBoard.setTotalSavingAccountNumber(totalSavingAccount);
		branchManagerDashBoard.setTotalCurrentAccount(branch.getAccounts().size() - totalSavingAccount);
		branchManagerDashBoard.setTotalCreditCardAccount(0);
		branchManagerDashBoard.setTotalDeposits(totalSavingAccount);
		branchManagerDashBoard.setRevenues(null);
		branchManagerDashBoard.setTotalFundTransfer(0);
		branchManagerDashBoard.setTotalDeposits(0);
		return branchManagerDashBoard;
	}

	private long getCountOfSavingAccount(List<Account> accounts) {

		return accounts.stream().filter(account -> account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT))
				.count();
	}
}
