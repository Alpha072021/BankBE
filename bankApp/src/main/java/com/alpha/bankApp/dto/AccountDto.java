package com.alpha.bankApp.dto;

import com.alpha.bankApp.enums.AccountType;
import com.alpha.bankApp.enums.Status;

public class AccountDto {
	private String userId;
	private String name;
	private long phoneNumber;
	private String emailID;
	private AccountType accountType;
	private String accountNumber;
	private Status status;

	public AccountDto(String userId, String name, long phoneNumber, String emailID, AccountType accountType,
			String accountNumber, Status status) {
		super();
		this.userId = userId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.emailID = emailID;
		this.accountType = accountType;
		this.accountNumber = accountNumber;
		this.status = status;
	}

	public AccountDto() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
