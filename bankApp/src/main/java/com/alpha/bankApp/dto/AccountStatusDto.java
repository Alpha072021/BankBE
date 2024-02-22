package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusDto {
	private String accountNumber;
	private Status status;
}
