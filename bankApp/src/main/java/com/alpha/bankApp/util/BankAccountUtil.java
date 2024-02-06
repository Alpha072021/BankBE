package com.alpha.bankApp.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.exception.BankAccountNotFoundException;

@Component
public class BankAccountUtil {
	@Autowired
	private BankAccountDao accountDao;

	public BankAccount updateBankAccount(long bankAccountId, BankAccount bankAccount) {
		BankAccount existingBankAccount = accountDao.getBankAccountById(bankAccountId);
		if (existingBankAccount != null) {
			if (bankAccount.getBankAccountId() == 0) {
				bankAccount.setBankAccountId(bankAccountId);
			}
			if (bankAccount.getBank() == null) {
				bankAccount.setBank(existingBankAccount.getBank());
			}
			if (bankAccount.getBankBalance() == 0.0) {
				bankAccount.setBankBalance(existingBankAccount.getBankBalance());
			}
			if (bankAccount.getCashInFlow() == 0.0) {
				bankAccount.setCashInFlow(existingBankAccount.getCashInFlow());
			}
			if (bankAccount.getCashOutFlow() == 0.0) {
				bankAccount.setCashOutFlow(existingBankAccount.getCashOutFlow());
			}
			List<BankLedger> bankLegders = bankAccount.getBankLegder();
			if (bankLegders == null || !bankLegders.isEmpty()) {
				bankAccount.setBankLegder(existingBankAccount.getBankLegder());
			}
			return bankAccount;
		}
		throw new BankAccountNotFoundException("BankAccount With The Given Id = " + bankAccountId + " Not Found");
	}

}
