package com.alpha.bankApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dto.AccountHoldersDto;
import com.alpha.bankApp.dto.BranchManagerDto;
import com.alpha.bankApp.dto.ManagingDirectorDashBoardDto;
import com.alpha.bankApp.dto.ManagingDirectorDto;
import com.alpha.bankApp.dto.RevenueDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.entity.idgenerator.AddressIdGenerator;
import com.alpha.bankApp.enums.AccountType;

@Component
public class EmployeeUtil {

	@Autowired
	private AddressIdGenerator generator;
	@Autowired
	private BankDao dao;
	@Autowired
	private BankAccountDao bankAccountDao;

	public Employee convertEmployeeInfo(Employee employee) {
		// To lowerCare the Employee Name
		if (employee.getName() != null)
			employee.setName(employee.getName().toLowerCase());
		// To lowerCare the Employee Email
		if (employee.getEmail() != null)
			employee.setEmail(employee.getEmail().toLowerCase());
		employee.setPassword("" + employee.getPhoneNumber());
		if (employee.getDateOfJoining() == null)
			employee.setDateOfJoining(LocalDateTime.now());
		// To Inject the Address Id
		if (employee.getAddress() != null) {
			employee.getAddress().setAddressId(generator.generate());
		}
		return employee;
	}

	public ManagingDirectorDto createManaginDirector(Employee employee, Bank bank) {
		if (bank != null) {
			return new ManagingDirectorDto(employee.getEmployeeId(), employee.getName(), employee.getEmail(),
					employee.getRole(), bank.getBankId(), bank.getBankName(), employee.getAddress());
		}
		return new ManagingDirectorDto(employee.getEmployeeId(), employee.getName(), employee.getEmail(),
				employee.getRole(), null, null, null);
	}

	public ManagingDirectorDto createManaginDirector(Employee employee) {

		return new ManagingDirectorDto(employee.getEmployeeId(), employee.getName(), employee.getRole());
	}

	public List<BranchManagerDto> getBranchManagers(Bank bank) {
		List<BranchManagerDto> branchManagerDtos = new ArrayList<>();
		for (Branch branch : bank.getBranches()) {
			if (branch.getBranchManager() != null) {
				branchManagerDtos.add(getBranchManagerDto(branch, bank.getBankName()));
			}
		}

		return branchManagerDtos;
	}

	public BranchManagerDto getBranchManagerDto(Branch branch, String bankName) {
		return new BranchManagerDto(branch.getBranchManager().getEmployeeId(), branch.getBranchManager().getName(),
				branch.getBranchManager().getEmail(), branch.getBranchId(), branch.getBranchName(), branch.getAddress(),
				branch.getIFSC(), branch.getBranchManager().getRole(), bankName);
	}

	public List<ManagingDirectorDto> createManaginDirector(List<Employee> list) {
		List<ManagingDirectorDto> directorDtos = new ArrayList<>();
		for (Employee employee : list) {
			Bank bank = dao.getBankByMdId(employee.getEmployeeId());
			directorDtos.add(createManaginDirector(employee, bank));
		}
		return directorDtos;
	}

	public ManagingDirectorDashBoardDto getDashBoard(String managingDirectorId) {
		ManagingDirectorDashBoardDto dashBoardDto = new ManagingDirectorDashBoardDto();
		Bank bank = dao.getBankByMdId(managingDirectorId);
		BankAccount bankAccount = bankAccountDao.getBankAccountByBankId(bank.getBankId()).get();
		dashBoardDto.setAccountHolders(generateAccountHolders(bank));
		dashBoardDto.setTotalAccounts(dashBoardDto.getAccountHolders().getTotalSavingAccounts()
				+ dashBoardDto.getAccountHolders().getTotalCurrentAccounts());
		dashBoardDto.setTotalBranches(bank.getBranches().size());
		dashBoardDto.setTotalDeposits(bankAccount.getCashInFlow());
		dashBoardDto.setTotalFundTransfer(bankAccount.getCashOutFlow());
		dashBoardDto.setTotalemployees(countOfEmployees(bank));
		dashBoardDto.setRevenues(createRevenues(bankAccount));
		return dashBoardDto;
	}

	public List<RevenueDto> createRevenues(BankAccount bankAccount) {
		List<BankLedger> bankLegders = bankAccount.getBankLegder();
		if (bankLegders != null && !(bankLegders.isEmpty())) {
			Collections.sort(bankLegders, (bankLegder1, bankLegder2) -> {
				return bankLegder1.getDate().compareTo(bankLegder2.getDate());
			});
			List<RevenueDto> revenueDtos = new ArrayList<>();
			for (int index = bankLegders.size() - 1; index >= 0 && index >= (bankLegders.size() - 5); index--) {
				BankLedger bankLedger = bankLegders.get(index);
				revenueDtos.add(new RevenueDto(bankLedger.getDate().toLocalDate(), bankLedger.getCashInFlow(),
						bankLedger.getCashOutFlow()));
			}
			revenueDtos.add(new RevenueDto(LocalDate.now(), bankAccount.getCashInFlow(), bankAccount.getCashOutFlow()));
			return revenueDtos;
		}

		return null;
	}

	public long countOfEmployees(Bank bank) {
		long numberOfEmployees = 0;
		for (Branch branch : bank.getBranches()) {
			if (branch.getBranchManager() != null) {
				numberOfEmployees++;
			}
		}
		return numberOfEmployees+1;
	}

	public AccountHoldersDto generateAccountHolders(Bank bank) {
		List<Branch> branchs = bank.getBranches();
		long numberOfSavingAcount = 0;
		long numberOfCurrentAccount = 0;
		for (Branch branch : branchs) {
			long savingAccountCount = branch.getAccounts().stream()
					.filter(account -> account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)).count();
			numberOfCurrentAccount += (branch.getAccounts().size() - savingAccountCount);
			numberOfSavingAcount += savingAccountCount;
		}
		return new AccountHoldersDto(numberOfSavingAcount, numberOfCurrentAccount, 0, 0);
	}
}
