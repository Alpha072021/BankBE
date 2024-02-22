package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchManagerDto {
	private String branchManagerId;
	private String branchManagerName;
	private String email;
	private String branchId;
	private String branchName;
	private Address address;
	private String IFSCCode;
	private Role role;
	private String bankName;

//	public BranchManagerDto(String branchManagerId, String branchManagerName, String email, String branchId,
//			String branchName, Address address, String iFSCCode, Role role) {
//		super();
//		this.branchManagerId = branchManagerId;
//		this.branchManagerName = branchManagerName;
//		this.email = email;
//		this.branchId = branchId;
//		this.branchName = branchName;
//		this.address = address;
//		IFSCCode = iFSCCode;
//		this.role = role;
//	}

}
