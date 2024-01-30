package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.util.ResponseStructure;

public interface BankLegderService {

	ResponseEntity<ResponseStructure<List<BankLedger>>> getBankLegder(String bankId);

	void generateBankLegder();

}
