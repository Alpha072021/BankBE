package com.alpha.bankApp.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountStatementDto {
	private String accountNumber;
	private LocalDate startDate;
	private LocalDate endDate;
}
