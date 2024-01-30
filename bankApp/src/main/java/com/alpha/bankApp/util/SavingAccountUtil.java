package com.alpha.bankApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dto.SavingAccountDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.entity.SavingsAccount;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.entity.idgenerator.AccountIdGenerator;
import com.alpha.bankApp.entity.idgenerator.DebitCardIdGenerator;
import com.alpha.bankApp.enums.AccountType;

@Component
public class SavingAccountUtil {
	@Autowired
	private AccountIdGenerator accountidGenerator;

	@Autowired
	private DebitCardIdGenerator debitCardIdGenerator;

	public SavingAccountDto getSavingAccountDto(Account account) {
		return new SavingAccountDto(account.getAccountHolder().getUserId(), account.getAccountHolder().getName(),
				account.getAccountHolder().getPhoneNumber(), account.getAccountHolder().getEmail(),
				account.getBranch().getIFSC(), account.getAccountNumber(), account.getStatus());
	}

	public List<SavingAccountDto> getSavingAccountDto(List<Account> accounts) {
		List<SavingAccountDto> accountDtos = new ArrayList<>();
		accounts = accounts.stream().filter(account -> account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT))
				.collect(Collectors.toList());
		for (Account account : accounts) {
			accountDtos.add(getSavingAccountDto(account));
		}
		return accountDtos;
	}

	public SavingsAccount generateAccountNumber(User user, SavingsAccount account, Branch branch, String bankId) {
		account.setCreationDateTime(LocalDateTime.now());
		account.setAccountType(AccountType.SAVINGS_ACCOUNT);
		// Generating accountNumber by Passing bankId and BranchId
		account.setAccountNumber(accountidGenerator.accountIdGenerator(bankId, branch.getBranchId()));
		account.setBranch(branch);
		// To Create DebitCard Id
		DebitCard card = new DebitCard();
		card.setAccount(account);
		// Generating DebitCardNumber by Passing bankId and AccountId
		card.setCardNumber(debitCardIdGenerator.debitCardIdGenerator(bankId, account.getAccountNumber()));
		card.setCreatedDateTime(LocalDateTime.now());
		LocalDate issuseDate = LocalDate.now();
		card.setIssueDate(issuseDate);
		LocalDate expirydate = LocalDate.of(issuseDate.getYear() + 3, issuseDate.getMonth(),
				issuseDate.getDayOfMonth());
		card.setExpiryDate(expirydate);
		card.setValidUptoDate(expirydate);
		account.setDebitCard(card);
		account.setAccountHolder(user);
		return account;
	}

}
