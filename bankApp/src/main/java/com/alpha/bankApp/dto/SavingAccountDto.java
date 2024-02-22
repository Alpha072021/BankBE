package com.alpha.bankApp.dto;

import com.alpha.bankApp.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SavingAccountDto {
	private String userId;
	private String name;
	private long phoneNumber;
	private String emailID;
	private String IFSCCode;
	private String accountNumber;
	private Status status;
	private DebitCardDto debitCardDto;

}
