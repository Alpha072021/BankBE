/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.BankLedgerDao;
import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.repository.BankLedgerJpaRepository;

/**
 * @author Vinod Raj
 */
@Repository
public class BankLedgerDaoImpl implements BankLedgerDao {

	@Autowired
	private BankLedgerJpaRepository jpaRepository;

	public BankLedger saveBankLedger(BankLedger bankLedger) {
		return jpaRepository.save(bankLedger);
	}

	@Override
	public BankLedger updateBankLedger(long bankLedgerId, BankLedger bankLedger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankLedger getBankLedgerById(long bankLedgerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteBankLedger(long bankLedgerId) {
		// TODO Auto-generated method stub
		return false;
	}

}
