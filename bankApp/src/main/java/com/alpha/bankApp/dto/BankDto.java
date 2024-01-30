package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.Address;

@Component
public class BankDto {
	private String bankId;
	private String bankName;
	private Address address;
	private String managingDirectorName;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getManagingDirectorName() {
		return managingDirectorName;
	}

	public void setManagingDirectorName(String managingDirectorName) {
		this.managingDirectorName = managingDirectorName;
	}

	public BankDto(String bankId, String bankName, Address address, String managingDirectorName) {
		super();
		this.bankId = bankId;
		this.bankName = bankName;
		this.address = address;
		this.managingDirectorName = managingDirectorName;
	}

	public BankDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
