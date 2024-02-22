package com.alpha.bankApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.entity.BankLedger;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.BankLegderService;
import com.alpha.bankApp.util.ResponseStructure;

@RestController
@RequestMapping("api/version/{version}/bankLegders")
public class BankLegderController {
	@Autowired
	private BankLegderService bankLegderService;

	@GetMapping
	public ResponseEntity<ResponseStructure<BankLedger>> getBankLegder(String version, String bankId) {
		if (version.equalsIgnoreCase("v1"))
			return bankLegderService.getBankLegder(bankId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
