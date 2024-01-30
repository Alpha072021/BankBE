package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.Address;

@Component
public class BranchDto {
	private String branchId;
	private String branchName;
	private Address address;
	private String IFSCCode;
	private String branchManagerName;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getIFSCCode() {
		return IFSCCode;
	}

	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}

	public String getBranchManagerName() {
		return branchManagerName;
	}

	public void setBranchManagerName(String branchManagerName) {
		this.branchManagerName = branchManagerName;
	}

	public BranchDto() {
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public BranchDto(String branchId, String branchName, Address address, String iFSCCode, String branchManagerName) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
		this.address = address;
		IFSCCode = iFSCCode;
		this.branchManagerName = branchManagerName;
	}

}
