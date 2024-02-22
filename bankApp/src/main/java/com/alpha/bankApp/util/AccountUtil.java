package com.alpha.bankApp.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.BankAccountDao;
import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dto.AccountDto;
import com.alpha.bankApp.dto.DebitCardDto;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.entity.Statement;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.entity.idgenerator.AccountIdGenerator;
import com.alpha.bankApp.entity.idgenerator.DebitCardIdGenerator;
import com.alpha.bankApp.enums.AccountType;
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
	@Autowired
	private BankAccountDao bankAccountDao;

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
		BankAccount bankAccount = bankAccountDao.getBankAccountByBankId(bankId).get();
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
			card.setCvv("" + (new Random().nextInt(900) + 100));
			account.setDebitCard(card);
			account.setAccountHolder(user);
			// Creating statement
			account.setStatement(createStatement());

			/* Developed a procedure to update the bank account balance and cash inflows. */
			bankAccount = updateBanckAccount(bankAccount, account);

			listAccounts.add(account);
		}
		return listAccounts;
	}

	/* Procedure to update the bank account balance and cash inflows. */
	public BankAccount updateBanckAccount(BankAccount bankAccount, Account account) {
		double accountBalance = 0;
		if (account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) {
			if (account.getAvailableBalance() != null)
				accountBalance = account.getAvailableBalance();
			else {
				accountBalance = 500.0;
			}
		} else {
			accountBalance = account.getCurrentBalance();
		}
		bankAccount.setBankBalance(bankAccount.getBankBalance() + accountBalance);
		bankAccount.setCashInFlow(bankAccount.getCashInFlow() + accountBalance);
		return bankAccount;
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
		if(account.getAvailableBalance()==null) {
			account.setAvailableBalance(0.0);
		}
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
				account.getAccountHolder().getDateOfBirth(), account.getAccountHolder().getAddress(), panNumber,
				(account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) ? account.getAvailableBalance()
						: account.getCurrentBalance(),
				createDebitCardDto(account));
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

	public Account modifiedAccount(AccountDto accountDto) {
		Account account = accountDao.getAccountByAccountNumber(accountDto.getAccountNumber());
		if (account != null) {
			account.getAccountHolder().setName(accountDto.getName());
			account.getAccountHolder().setEmail(accountDto.getEmailID());
			account.getAccountHolder().setPhoneNumber(accountDto.getPhoneNumber());
			account.getAccountHolder().setDateOfBirth(accountDto.getDateOfBirth());
			account.setStatus(accountDto.getStatus());
			account.getAccountHolder().setAddress(modifyAddress(account.getAccountHolder().getAddress(), accountDto));
			/*
			 * Made changes to the implementation to update user accounts based on
			 * modifications made by users to their balances.
			 */
			if (account.getAccountType().equals(AccountType.SAVINGS_ACCOUNT)) {
				account.setAvailableBalance(accountDto.getAccountBalance());
			} else if (account.getAccountType().equals(AccountType.CURRENT_ACCOUNT)) {
				account.setCurrentBalance(accountDto.getAccountBalance());
			}
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
