package com.alpha.bankApp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.exception.BankLedgerNotFound;
import com.alpha.bankApp.service.BankLegderService;
import com.alpha.bankApp.util.BankLegderUtil;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BankLegderServiceImpl implements BankLegderService {
	@Autowired
	private BankAccountDao bankAccountDao;
	@Autowired
	private BankLegderUtil bankLegderUtil;

	@Override
	public ResponseEntity<ResponseStructure<BankLedger>> getBankLegder(String bankId) {
		BankAccount bankAccount = bankAccountDao.getBankAccountByBankId(bankId).get();
		List<BankLedger> bankLegders = bankAccount.getBankLegder();
		if (bankLegders != null && !(bankLegders.isEmpty())) {
			Collections.sort(bankLegders, (bankLegder1, bankLegder2) -> {
				return bankLegder1.getDate().compareTo(bankLegder2.getDate());
			});
			ResponseStructure<BankLedger> structure = new ResponseStructure<>();
			structure.setData(bankLegders.get(bankLegders.size() - 1));
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<BankLedger>>(structure, HttpStatus.OK);
		}
		throw new BankLedgerNotFound("The BankLedger decided to take a day off and wasn't created.");
	}

	/*
	 * successfully implemented the BankLedger feature at 12 o'clock.
	 * Modified the fetch type from LAZY to EAGER in order to dynamically generate the bankLedger.
	 */
	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	public void generateBankLegder() {
		List<BankAccount> bankAccounts = bankAccountDao.findAllBankAccounts();
		List<BankAccount> modifiedBankAccounts = new ArrayList<>();
		for (BankAccount bankAccount : bankAccounts) {
			modifiedBankAccounts.add(bankLegderUtil.generateBankLegder(bankAccount));
		}
		bankAccountDao.updateBankAccount(modifiedBankAccounts);
	}

}
