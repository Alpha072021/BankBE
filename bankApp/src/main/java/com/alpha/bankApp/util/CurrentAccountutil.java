package com.alpha.bankApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dto.CurrentAccountDto;
import com.alpha.bankApp.dto.DebitCardDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.CurrentAccount;
import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.entity.idgenerator.AccountIdGenerator;
import com.alpha.bankApp.entity.idgenerator.DebitCardIdGenerator;
import com.alpha.bankApp.enums.AccountType;

@Component
public class CurrentAccountutil {
	@Autowired
	private AccountIdGenerator accountidGenerator;

	@Autowired
	private DebitCardIdGenerator debitCardIdGenerator;

	public List<CurrentAccountDto> getCurrentAccountDto(List<Account> accounts) {
		List<CurrentAccountDto> accountDtos = new ArrayList<>();
		accounts = accounts.stream().filter(account -> account.getAccountType().equals(AccountType.CURRENT_ACCOUNT))
				.collect(Collectors.toList());
		for (Account account : accounts) {
			accountDtos.add(getCurrentAccountDto(account));
		}
		return accountDtos;
	}

	public CurrentAccountDto getCurrentAccountDto(Account account) {

		return new CurrentAccountDto(account.getAccountHolder().getUserId(), account.getAccountHolder().getName(),
				account.getAccountHolder().getPhoneNumber(), account.getAccountHolder().getEmail(),
				account.getBranch().getIFSC(), account.getAccountNumber(), account.getStatus(),
				createDebitCardDto(account));
	}

	public CurrentAccount generateAccountNumber(User user, CurrentAccount account, Branch branch, String bankId) {
		account.setCreationDateTime(LocalDateTime.now());
		// Generating accountNumber by Passing bankId and BranchId
		account.setAccountNumber(accountidGenerator.accountIdGenerator(bankId, branch.getBranchId()));
		account.setBranch(branch);
		account.setAccountType(AccountType.CURRENT_ACCOUNT);
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

	private DebitCardDto createDebitCardDto(Account account) {
		if (account.getDebitCard() != null) {
			String debitCardNumber = "X".repeat(account.getDebitCard().getCardNumber().length() - 4) + account
					.getDebitCard().getCardNumber().substring(account.getDebitCard().getCardNumber().length() - 4);

			return new DebitCardDto(debitCardNumber, account.getDebitCard().getStatus(),
					account.getDebitCard().getExpiryDate(), account.getDebitCard().getIssueDate(),
					account.getDebitCard().getValidUptoDate(), account.getDebitCard().getApproval());
		}
		return null;
	}

}
