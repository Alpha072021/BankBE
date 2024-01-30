/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao;

import java.util.List;

import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.enums.Status;

/**
 * @author Vinod Raj
 */
public interface AccountDao {

	public Account updateAccount(String accountNumber, Account account);

	public Account getAccountByAccountNumber(String accountNumber);

	public List<Account> getAllAccounts();

	public boolean removeAccount(String accountNumber);

	public Account getAccountByStatus(Status status);

	public List<Account> findAllAccountByBranchId(String branchId);

	// To Generate Account Id
	public String findLastAccountId();

	public List<Account> getAllAccounts(String branchId);
}
