package com.alpha.bankApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dto.AccountDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.entity.idgenerator.AccountIdGenerator;
import com.alpha.bankApp.entity.idgenerator.DebitCardIdGenerator;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.exception.UserNotFoundException;

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
	@Autowired
	private AccountDao accountDao;

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
			// Creating statement
			account.setStatement(createStatement());
			listAccounts.add(account);
		}
		return listAccounts;
	}

	public Statement createStatement() {
		Statement statement = new Statement();
		statement.setCreatedDateAndTime(LocalDateTime.now());
		statement.setTransactions(new ArrayList<>());
		return statement;
	}

	public List<AccountDto> getAccountDto(List<Account> accounts) {
		List<AccountDto> accountDtos = new ArrayList<>();
		for (Account account : accounts) {
			accountDtos.add(getAccountDto(account));
		}
		return accountDtos;
	}

	public AccountDto getAccountDto(Account account) {
		DocsContainer docsContainer = account.getAccountHolder().getDocsContainer();
		String panNumber = null;
		if (docsContainer != null) {
			List<Document> documents = docsContainer.getDocuments();
			if (documents != null && !(documents.isEmpty())) {
				for (Document document : documents) {
					if (document.getType().equals(DocumentType.PAN_CARD)) {
						panNumber = document.getDocumentNumber();
					}
				}
			}
		}
		return new AccountDto(account.getAccountHolder().getUserId(), account.getAccountHolder().getName(),
				account.getAccountHolder().getPhoneNumber(), account.getAccountHolder().getEmail(),
				account.getAccountType(), account.getAccountNumber(), account.getStatus(),
				account.getAccountHolder().getDateOfBirth(), account.getAccountHolder().getAddress(), panNumber);
	}

	public Account modifiedAccount(AccountDto accountDto) {
		Account account = accountDao.getAccountByAccountNumber(accountDto.getAccountNumber());
		if (account != null) {
			account.getAccountHolder().setName(accountDto.getName());
			account.getAccountHolder().setDateOfBirth(accountDto.getDateOfBirth());
			account.setStatus(accountDto.getStatus());
			account.getAccountHolder().setAddress(modifyAddress(account.getAccountHolder().getAddress(), accountDto));
			return account;
		}
		throw new UserNotFoundException("User not holds Account");
	}

	public Address modifyAddress(Address address, AccountDto accountDto) {
		if (accountDto.getAddress().getAddressLine() != null) {
			address.setAddressLine(accountDto.getAddress().getAddressLine());
		}
		if (accountDto.getAddress().getCity() != null) {
			address.setCity(accountDto.getAddress().getCity());
		}
		if (accountDto.getAddress().getState() != null) {
			address.setState(accountDto.getAddress().getState());
		}
		if (accountDto.getAddress().getCountry() != null) {
			address.setCountry(accountDto.getAddress().getCountry());
		}
		if (accountDto.getAddress().getPincode() != null) {
			address.setPincode(accountDto.getAddress().getPincode());
		}
		return address;
	}

}
