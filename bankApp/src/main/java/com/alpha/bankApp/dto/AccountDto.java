package com.alpha.bankApp.dto;

import java.time.LocalDate;

import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.enums.AccountType;
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
public class AccountDto {
	private String userId;
	private String name;
	private long phoneNumber;
	private String emailID;
	private AccountType accountType;
	private String accountNumber;
	private Status status;
	private LocalDate dateOfBirth;
	private Address address;
	private String panNumber;

}
