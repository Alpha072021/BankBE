package com.alpha.bankApp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.BankLedger;

@Component
public class BankLegderUtil {

	public BankAccount generateBankLegder(BankAccount bankAccount) {
		List<BankLedger> bankLegders = bankAccount.getBankLegder();
		boolean flag = bankLegders != null && !bankLegders.isEmpty();
		bankLegders = new ArrayList<>();
		/*
		 * To obtain the opening balance for new bank ledgers, consider sorting the bank
		 * ledger entries.
		 */

		if (flag) {
			Collections.sort(bankLegders, (bankLegder1, bankLegder2) -> {
				return bankLegder1.getDate().compareTo(bankLegder2.getDate());
			});
		}
		/*
		 * generating a new bank ledger using the cash inflows and outflows data from
		 * the bank account.
		 */
		BankLedger bankLegder = new BankLedger();
		bankLegder.setCashInFlow(bankAccount.getCashInFlow());
		bankLegder.setCashOutFlow(bankAccount.getCashOutFlow());
		/* made adjustments to the implementation for generating the bank ledger. */
		bankLegder.setDate(LocalDateTime.now().minusDays(1));
		bankLegder.setClosingBalance(bankAccount.getBankBalance());
		if (flag) {
			bankLegder.setOpeningBalance(bankLegders.get(bankLegders.size() - 1).getClosingBalance());
		} else {
			bankLegder.setOpeningBalance(0);
		}
		bankLegder.setBankAccount(bankAccount);
		bankLegders.add(bankLegder);
		bankAccount.setBankLegder(bankLegders);
		/*
		 * adjusted the cash inflows and outflows data in the bank ledger, resulting in
		 * a balance of zero for the day.
		 */
		bankAccount.setCashInFlow(0);
		bankAccount.setCashOutFlow(0);
		return bankAccount;
	}

}
