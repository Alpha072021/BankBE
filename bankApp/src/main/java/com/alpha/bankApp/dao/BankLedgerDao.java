/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao;

import com.alpha.bankApp.entity.BankLedger;

/**
 * @author Vinod Raj
 */
public interface BankLedgerDao {

	public BankLedger saveBankLedger(BankLedger bankLedger);

	public BankLedger updateBankLedger(long bankLedgerId, BankLedger bankLedger);

	public BankLedger getBankLedgerById(long bankLedgerId);

	public boolean deleteBankLedger(long bankLedgerId);

}
