package com.alpha.bankApp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dto.BankDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.BankAccount;
import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.entity.idgenerator.AddressIdGenerator;
import com.alpha.bankApp.exception.InvalidBankNameException;

@Component
public class BankUtil {
	@Autowired
	private AddressIdGenerator generator;
	@Autowired
	private BankDao bankDao;

	public Bank convertBankInfo(Bank bank) {
		if (bank.getBankName() != null) {
			if (bank.getBankName().length() > 4) {
				if (!(bank.getBankName().substring(0, 4).contains(" "))) {
					if (bank.getBankName() != null) {
						bank.setBankName(bank.getBankName().toLowerCase());
					}
					if (bank.getAddress() != null) {
						bank.getAddress().setAddressId(generator.generate());
					}
					if (bank.getBankCreated() == null) {
						bank.setBankCreated(LocalDateTime.now());
					}
					return bank;
				}
				throw new InvalidBankNameException(
						"Why would anyone put spaces in the first four characters of a bank name? That's just silly.");
			}
			throw new InvalidBankNameException(
					"Why would anyone use less than four characters of a bank name?  That's just silly.");
		}
		throw new InvalidBankNameException(
				"What might be the reason for omitting the bank name when creating bank details?? That's just silly.");
	}

	public BankDto getBankDto(Bank bank) {
		if (bank.getManagingDirector() != null) {
			return new BankDto(bank.getBankId(), bank.getBankName(), bank.getAddress(),
					bank.getManagingDirector().getName());
		}
		return new BankDto(bank.getBankId(), bank.getBankName(), bank.getAddress(), "UnAssigned");
	}

	public List<BankDto> getBankDto(List<Bank> banks) {
		List<BankDto> bankDtos = new ArrayList<>();
		for (Bank bank : banks) {
			bankDtos.add(getBankDto(bank));
		}
		return bankDtos;
	}

	public Bank modifiedBank(String bankId, Bank bank) {
		Bank exsistingBank = bankDao.getBank(bankId);
		if (exsistingBank != null) {
			bank.setBankCreated(exsistingBank.getBankCreated());
		}
		return bank;
	}

	public BankAccount createBankAccount(Bank bank) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBank(bank);
		bankAccount.setBankLegder(new ArrayList<BankLedger>());
		return bankAccount;
	}

}
