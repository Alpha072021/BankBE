package com.alpha.bankApp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dto.BankDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.idgenerator.AddressIdGenerator;

@Component
public class BankUtil {
	@Autowired
	private AddressIdGenerator generator;
	@Autowired
	private BankDao bankDao;

	public Bank convertBankInfo(Bank bank) {
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

}
