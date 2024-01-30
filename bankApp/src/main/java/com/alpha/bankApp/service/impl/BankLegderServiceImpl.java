package com.alpha.bankApp.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.service.BankLegderService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class BankLegderServiceImpl implements BankLegderService {

	@Override
	public ResponseEntity<ResponseStructure<List<BankLedger>>> getBankLegder(String bankId) {
		// need to implement to generate the BankLegder Based on the bankID
		return null;
	}

	@Override
	@Scheduled(cron = "0 0 12 * * ?")
	public void generateBankLegder() {
		// need to implement to generate BankLegder very at 12

	}

}
