/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.CurrentAccountDao;
import com.alpha.bankApp.entity.CurrentAccount;
import com.alpha.bankApp.repository.AccountJpaRepository;

/**
 * @author Vinod Raj
 */
@Repository
public class CurrrentAccountDaoImpl extends AccountDaoImpl implements CurrentAccountDao {

	@Autowired
	private AccountJpaRepository accountRepository;

	@Override
	public CurrentAccount saveCurrentAccount(CurrentAccount account) {
		return accountRepository.save(account);
	}

}
