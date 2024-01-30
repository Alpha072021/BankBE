package com.alpha.bankApp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dao.SavingAccountDao;
import com.alpha.bankApp.dao.UserDao;
import com.alpha.bankApp.dto.SavingAccountDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.SavingsAccount;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.enums.AccountType;
import com.alpha.bankApp.exception.BankNotAssignedBranchException;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.exception.CustomersNotHaveAccount;
import com.alpha.bankApp.exception.UserNotFoundException;
import com.alpha.bankApp.service.SavingAccountService;
import com.alpha.bankApp.util.ResponseStructure;
import com.alpha.bankApp.util.SavingAccountUtil;

@Service
public class SavingAccountServiceImpl extends AccountServiceImpl implements SavingAccountService {
	@Autowired
	private SavingAccountDao accountDao;

	@Autowired
	private SavingAccountUtil savingAccountUtil;

	@Autowired
	private BankDao bankDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BranchDao branchDao;

	@Override
	public ResponseEntity<ResponseStructure<List<SavingAccountDto>>> getAllSavingAccountsByBranchId(String branchId) {
		List<Account> accounts = accountDao.getAllAccounts(branchId);
		if (accounts != null && !accounts.isEmpty()) {
			ResponseStructure<List<SavingAccountDto>> structure = new ResponseStructure<>();
			structure.setData(savingAccountUtil.getSavingAccountDto(accounts));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<SavingAccountDto>>>(structure, HttpStatus.OK);
		}
		throw new CustomersNotHaveAccount("Customers Not Have Any Account In the Given Branch");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<SavingAccountDto>>> getAllSavingAccountsBybankId(String bankId) {
		Bank bank = bankDao.getBank(bankId);
		if (bank != null) {
			if (bank.getBranches() != null && !bank.getBranches().isEmpty()) {
				List<Account> accounts = new ArrayList<>();
				for (Branch branch : bank.getBranches()) {
					if (branch.getAccounts() != null && !branch.getAccounts().isEmpty()) {
						accounts.addAll(branch.getAccounts());
					}
				}
				// After this for-loop we will receive all the account Belongs to bank
				if (!accounts.isEmpty()) {
					ResponseStructure<List<SavingAccountDto>> structure = new ResponseStructure<>();
					structure.setData(savingAccountUtil.getSavingAccountDto(accounts));
					structure.setMessage("Found");
					structure.setStatusCode(HttpStatus.OK.value());
					return new ResponseEntity<ResponseStructure<List<SavingAccountDto>>>(structure, HttpStatus.OK);
				}
				throw new CustomersNotHaveAccount("Customers Not Have Any Account in the Given Bank");
			}
			throw new BankNotAssignedBranchException("Bank Not Have Any Branches");
		}
		throw new BankNotFoundException("Bank With the Given Id " + bankId + " Not exsits");
	}

	@Override
	public ResponseEntity<ResponseStructure<SavingAccountDto>> getSavingAccountByAccountNumber(String accountNumber) {
		Account account = accountDao.getAccountByAccountNumber(accountNumber);
		if (account != null && account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) {
			ResponseStructure<SavingAccountDto> structure = new ResponseStructure<>();
			structure.setData(savingAccountUtil.getSavingAccountDto(account));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<SavingAccountDto>>(structure, HttpStatus.OK);
		}
		throw new CustomersNotHaveAccount(
				"Saving-Account With the Given Account-Number " + accountNumber + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<SavingsAccount>> saveAccount(String userId, String branchId,
			SavingsAccount account) {
		Branch branch = branchDao.getBranch(branchId);
		String bankId = bankDao.findBankIdByBranchId(branchId);
		if (branch != null) {
			Optional<User> optional = userDao.findUserById(userId);
			if (optional.isPresent()) {
				User user = optional.get();
				List<Account> accounts = user.getAccounts();
				if (accounts == null) {
					accounts = new ArrayList<>();
				}

				account = savingAccountUtil.generateAccountNumber(user, account, branch, bankId);
				account = accountDao.saveSavingsAccount(account);
				accounts.add(account);
				user.setAccounts(accounts);
				userDao.saveUser(user);
				ResponseStructure<SavingsAccount> structure = new ResponseStructure<>();
				structure.setData(account);
				structure.setMessage("Created");
				structure.setStatusCode(HttpStatus.CREATED.value());
				return new ResponseEntity<ResponseStructure<SavingsAccount>>(structure, HttpStatus.CREATED);

			}
			throw new UserNotFoundException("User With The Given Id " + userId + " Not Found");
		}
		throw new BranchNotFoundException("Branch With the Given Id " + branchId + " Not Found");
	}

}
