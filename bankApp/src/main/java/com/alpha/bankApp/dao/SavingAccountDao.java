/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao;

import com.alpha.bankApp.entity.SavingsAccount;

/**
 * @author Vinod Raj
 */
public interface SavingAccountDao extends AccountDao {

	public SavingsAccount saveSavingsAccount(SavingsAccount account);

}
