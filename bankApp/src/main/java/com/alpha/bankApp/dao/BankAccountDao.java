/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.alpha.bankApp.entity.BankAccount;

/**
 * @author Vinod Raj
 */
public interface BankAccountDao {

	public BankAccount saveBankAccount(BankAccount bankAccount);

	public BankAccount getBankAccountById(long bankAccountId);

	public BankAccount updateBankAccountById(long bankAccountId, BankAccount account);

	public boolean deleteBankAccountById(long bankAccountId);

	// To Get the BankAccout based on the BankId
	public Optional<BankAccount> getBankAccountByBankId(String bankId);

	public ArrayList<Long> getBankAccountIdByBankId(String bankId);

	/* created for retrieving all bankAccounts to update the bankLedger. */
	List<BankAccount> findAllBankAccounts();

	/* updating BankAccounts after successfully creating bankLedgers. */
	void updateBankAccount(List<BankAccount> modifiedBankAccounts);

}
