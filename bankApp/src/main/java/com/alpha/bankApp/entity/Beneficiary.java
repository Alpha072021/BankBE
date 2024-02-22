package com.alpha.bankApp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long beneficiaryId;
	private String senderAccountNumber;
	private String reciverAccountNumber;
	private String reciverBranchId;
	private String branchName;
	private String IFSCCode;
	private double beneficiaryTransferLimit;
	private String beneficiaryName;
	private LocalDateTime createdDateTime;
}
