package com.alpha.bankApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dto.AccountDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.entity.idgenerator.AccountIdGenerator;
import com.alpha.bankApp.entity.idgenerator.DebitCardIdGenerator;

@Component
public class AccountUtil {
	@Autowired
	private AccountIdGenerator accountidGenerator;
	@Autowired
	private BankDao bankDao;
	@Autowired
	private DebitCardIdGenerator debitCardIdGenerator;
	@Autowired
	private BranchDao branchDao;

	public List<User> getUsers(List<Account> accounts) {
		List<User> users = new ArrayList<>();
		for (Account account : accounts) {
			User user = account.getAccountHolder();
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}

	public List<Account> createAccountId(User user, String branchId) {
		String bankId = bankDao.findBankIdByBranchId(branchId);
		Branch branch = branchDao.getBranch(branchId);
		List<Account> listAccounts = new ArrayList<>();
		for (Account account : user.getAccounts()) {
			// Setting the Creating DateAndTime for Account
			account.setCreationDateTime(LocalDateTime.now());
			// Generating accountNumber by Passing bankId and BranchId
			account.setAccountNumber(accountidGenerator.accountIdGenerator(bankId, branchId));
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
			listAccounts.add(account);
		}
		return listAccounts;
	}

	public List<AccountDto> getAccountDto(List<Account> accounts) {
		List<AccountDto> accountDtos = new ArrayList<>();
		for (Account account : accounts) {
			accountDtos.add(getAccountDto(account));
		}
		return accountDtos;
	}

	public AccountDto getAccountDto(Account account) {
		return new AccountDto(account.getAccountHolder().getUserId(), account.getAccountHolder().getName(),
				account.getAccountHolder().getPhoneNumber(), account.getAccountHolder().getEmail(),
				account.getAccountType(), account.getAccountNumber(), account.getStatus());
	}

}
