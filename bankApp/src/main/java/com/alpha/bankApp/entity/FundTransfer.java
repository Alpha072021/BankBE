package com.alpha.bankApp.entity;

import com.alpha.bankApp.enums.FoundMessage;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("FundTransfer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FundTransfer extends Transaction {
	private String senderAccountNumber;
	private String receiverAccountNumber;
	private double amount;
	@Enumerated(EnumType.STRING)
	private FoundMessage foundMessage;
}
