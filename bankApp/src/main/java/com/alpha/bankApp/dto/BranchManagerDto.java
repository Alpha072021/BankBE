package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.enums.Role;

@Component
public class BranchManagerDto {
	private String branchManagerId;
	private String branchManagerName;
	private String email;
	private String branchId;
	private String branchName;
	private Address address;
	private String IFSCCode;
	private Role role;

	public String getBranchManagerName() {
		return branchManagerName;
	}

	public void setBranchManagerName(String branchManagerName) {
		this.branchManagerName = branchManagerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public BranchManagerDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBranchManagerId() {
		return branchManagerId;
	}

	public void setBranchManagerId(String branchManagerId) {
		this.branchManagerId = branchManagerId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public BranchManagerDto(String branchManagerId, String branchManagerName, String email, String branchId,
			String branchName, Address address, String iFSCCode, Role role) {
		super();
		this.branchManagerId = branchManagerId;
		this.branchManagerName = branchManagerName;
		this.email = email;
		this.branchId = branchId;
		this.branchName = branchName;
		this.address = address;
		IFSCCode = iFSCCode;
		this.role = role;
	}

}
