package com.alpha.bankApp.dto;

import java.util.List;

import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.enums.EducationalQualification;
import com.alpha.bankApp.enums.OccupationType;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.enums.UserType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

	private String userId;
	private String name;
	private long phoneNumber;
	private String email;
	private String gender;
	private String token;
	private EducationalQualification qualification;
	private OccupationType occupationType;
	private double annualIncome;
	private Status status;
	private UserType userType;
	private Address address;
	private List<Account> accounts;
	private Bank bank;
}