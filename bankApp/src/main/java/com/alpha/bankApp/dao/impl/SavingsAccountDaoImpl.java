/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.SavingAccountDao;
import com.alpha.bankApp.entity.SavingsAccount;
import com.alpha.bankApp.repository.AccountJpaRepository;

/**
 * @author Vinod Raj
 */
@Repository
public class SavingsAccountDaoImpl extends AccountDaoImpl implements SavingAccountDao {

	@Autowired
	private AccountJpaRepository accountRepository;

	@Override
	public SavingsAccount saveSavingsAccount(SavingsAccount account) {
		return accountRepository.save(account);
	}

}
