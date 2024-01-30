/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao;

import com.alpha.bankApp.entity.BankAccount;

/**
 * @author Vinod Raj
 */
public interface BankAccountDao {
	
	public BankAccount saveBankAccount(BankAccount bankAccount);
	
	public BankAccount getBankAccountById(long bankAccountId);
	
	public BankAccount updateBankAccountById(long bankAccountId, BankAccount account);
	
	public boolean deleteBankAccountById(long bankAccountId);
	

}
