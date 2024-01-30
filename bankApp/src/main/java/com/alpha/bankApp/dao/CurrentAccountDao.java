/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao;

import com.alpha.bankApp.entity.CurrentAccount;

/**
 * @author Vinod Raj
 */
public interface CurrentAccountDao extends AccountDao {

	public CurrentAccount saveCurrentAccount(CurrentAccount account);

}
