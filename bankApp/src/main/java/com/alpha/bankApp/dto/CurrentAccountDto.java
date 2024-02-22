package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CurrentAccountDto {
	private String userId;
	private String name;
	private long phoneNumber;
	private String emailID;
	private String IFSCCode;
	private String accountNumber;
	private Status status;
	private DebitCardDto debitCardDto;

}
