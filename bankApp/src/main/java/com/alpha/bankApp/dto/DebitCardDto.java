package com.alpha.bankApp.dto;

import java.time.LocalDate;

import com.alpha.bankApp.enums.Approval;
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
public class DebitCardDto {
	private String debitCardNumber;
	private Status status;
	private LocalDate expiryDate;
	private LocalDate issueDate;
	private LocalDate validUptoDate;
	private Approval approval;
}
