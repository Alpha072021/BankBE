package com.alpha.bankApp.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StatementDto {
	private LocalDateTime date;
	private String narration;
	private double debit;
	private double credit;
	private double balance;
}
