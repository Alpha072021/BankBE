/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.repository.AccountJpaRepository;

/**
 * @author Vinod Raj
 */
@Repository
@Primary
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private AccountJpaRepository accountRepository;

	@Override
	public Account updateAccount(String accountNumber, Account account) {

		var dbAccount = accountRepository.findById(accountNumber);
		if (dbAccount.isPresent()) {
			account.setAccountNumber(accountNumber);
			return accountRepository.save(account);
		}
		return null;
	}

	@Override
	public Account getAccountByAccountNumber(String accountNumber) {

		var account = accountRepository.findById(accountNumber);

		return account.isPresent() ? account.get() : null;

	}

	@Override
	public List<Account> getAllAccounts() {

		return accountRepository.findAll();
	}

	@Override
	public boolean removeAccount(String accountNumber) {
		var account = accountRepository.findById(accountNumber);
		if (account.isPresent()) {
			accountRepository.delete(account.get());
			return true;
		} else
			return false;

	}

	@Override
	public Account getAccountByStatus(Status status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAllAccountByBranchId(String branchId) {
		return accountRepository.findAllByBranchId(branchId);

	}

	@Override
	public String findLastAccountId() {

		return accountRepository.findLastAccountId();
	}

	@Override
	public List<Account> getAllAccounts(String branchId) {

		return accountRepository.findAllByBranchId(branchId);
	}

}
