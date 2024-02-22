package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

import com.alpha.bankApp.enums.Approval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AccountApprovalDto {
	private Approval approval;
	private String accountNumber;
}
