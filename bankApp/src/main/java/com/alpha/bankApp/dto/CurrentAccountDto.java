package com.alpha.bankApp.dto;

import com.alpha.bankApp.enums.Status;

public class CurrentAccountDto {
	private String userId;
	private String name;
	private long phoneNumber;
	private String emailID;
	private String IFSCCode;
	private String accountNumber;
	private Status status;

	public CurrentAccountDto() {
	}

	public CurrentAccountDto(String userId, String name, long phoneNumber, String emailID, String iFSCCode,
			String accountNumber, Status status) {
		super();
		this.userId = userId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.emailID = emailID;
		IFSCCode = iFSCCode;
		this.accountNumber = accountNumber;
		this.status = status;
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

	public String getIFSCCode() {
		return IFSCCode;
	}

	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
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
