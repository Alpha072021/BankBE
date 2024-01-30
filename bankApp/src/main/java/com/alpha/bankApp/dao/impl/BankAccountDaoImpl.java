/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.repository.BankAccountJpaRepository;

/**
 * @author Vinod Raj
 */
@Repository
public class BankAccountDaoImpl implements BankAccountDao {

	@Autowired
	private BankAccountJpaRepository jpaRepository;

	@Override
	public BankAccount saveBankAccount(BankAccount bankAccount) {
		return jpaRepository.save(bankAccount);
	}

	@Override
	public BankAccount getBankAccountById(long bankAccountId) {
		Optional<BankAccount> account = jpaRepository.findById(bankAccountId);
		if (account.isPresent()) {
			return account.get();
		}
		return null;
	}

	@Override
	public BankAccount updateBankAccountById(long bankAccountId, BankAccount bankAccount) {
		BankAccount repositoryAccount = getBankAccountById(bankAccountId);
		if (repositoryAccount != null) {
			bankAccount.setBankAccountId(bankAccountId);
			return jpaRepository.save(bankAccount);
		}
		return null;
	}

	@Override
	public boolean deleteBankAccountById(long bankAccountId) {
		BankAccount repositoryAccount = getBankAccountById(bankAccountId);
		if (repositoryAccount != null) {
			jpaRepository.delete(repositoryAccount);
			return true;
		}
		return false;
	}

}
