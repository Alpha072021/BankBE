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
import com.alpha.bankApp.dao.CurrentAccountDao;
import com.alpha.bankApp.dao.UserDao;
import com.alpha.bankApp.dto.CurrentAccountDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.CurrentAccount;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.exception.BankNotAssignedBranchException;
import com.alpha.bankApp.exception.BankNotFoundException;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.exception.CustomersNotHaveAccount;
import com.alpha.bankApp.exception.UserNotFoundException;
import com.alpha.bankApp.service.CurrentAccountService;
import com.alpha.bankApp.util.CurrentAccountutil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class CurrentAccountServiceImpl extends AccountServiceImpl implements CurrentAccountService {
	@Autowired
	private CurrentAccountDao accountDao;
	@Autowired
	private CurrentAccountutil accountUtil;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private UserDao userDao;

	@Override
	public ResponseEntity<ResponseStructure<List<CurrentAccountDto>>> getAllCurentAccountsByBranchId(String branchId) {
		List<Account> accounts = accountDao.getAllAccounts(branchId);
		if (accounts != null && !accounts.isEmpty()) {
			ResponseStructure<List<CurrentAccountDto>> structure = new ResponseStructure<>();
			structure.setData(accountUtil.getCurrentAccountDto(accounts));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<CurrentAccountDto>>>(structure, HttpStatus.OK);
		}
		throw new CustomersNotHaveAccount("Customers Not Have Any Account In the Given Branch");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<CurrentAccountDto>>> getAllCurrentAccountsBybankId(String bankId) {
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
					ResponseStructure<List<CurrentAccountDto>> structure = new ResponseStructure<>();
					structure.setData(accountUtil.getCurrentAccountDto(accounts));
					structure.setMessage("Found");
					structure.setStatusCode(HttpStatus.OK.value());
					return new ResponseEntity<ResponseStructure<List<CurrentAccountDto>>>(structure, HttpStatus.OK);
				}
				throw new CustomersNotHaveAccount("Customers Not Have Any Account in the Given Bank");
			}
			throw new BankNotAssignedBranchException("Bank Not Have Any Branches");
		}
		throw new BankNotFoundException("Bank With the Given Id " + bankId + " Not exsits");
	}

	@Override
	public ResponseEntity<ResponseStructure<CurrentAccountDto>> getCurrentAccountByAccountNumber(String accountNumber) {
		Account account = accountDao.getAccountByAccountNumber(accountNumber);
		if (account != null) {
			ResponseStructure<CurrentAccountDto> structure = new ResponseStructure<>();
			structure.setData(accountUtil.getCurrentAccountDto(account));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<CurrentAccountDto>>(structure, HttpStatus.OK);
		}
		throw new CustomersNotHaveAccount(
				"Current-Account With the Given Account-Number " + accountNumber + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<CurrentAccount>> saveAccount(String userId, String branchId,
			CurrentAccount account) {
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

				account = accountUtil.generateAccountNumber(user, account, branch, bankId);
				account = accountDao.saveCurrentAccount(account);
				accounts.add(account);
				user.setAccounts(accounts);
				userDao.saveUser(user);
				ResponseStructure<CurrentAccount> structure = new ResponseStructure<>();
				structure.setData(account);
				structure.setMessage("Created");
				structure.setStatusCode(HttpStatus.CREATED.value());
				return new ResponseEntity<ResponseStructure<CurrentAccount>>(structure, HttpStatus.CREATED);

			}
			throw new UserNotFoundException("User With The Given Id " + userId + " Not Found");
		}
		throw new BranchNotFoundException("Branch With the Given Id " + branchId + " Not Found");
	}

}
