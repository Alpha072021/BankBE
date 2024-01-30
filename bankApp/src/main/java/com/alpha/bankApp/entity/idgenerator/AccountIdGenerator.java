package com.alpha.bankApp.entity.idgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.AccountDao;

@Component
public class AccountIdGenerator {
	@Autowired
	private AccountDao accountDao;

	public String accountIdGenerator(String bankId, String branchId) {
		String accountId = "";
		String lastAccountId = accountDao.findLastAccountId();
		if (lastAccountId != null) {
			int id = (Integer.parseInt(lastAccountId.substring(7, lastAccountId.length())) + 1);
			if (id <= 9) {
				accountId += bankId.substring(4) + branchId.substring(4) + "000000" + id;
			} else if (id <= 99) {
				accountId += bankId.substring(4) + branchId.substring(4) + "00000" + id;
			} else if (id <= 999) {
				accountId += bankId.substring(4) + branchId.substring(4) + "0000" + id;
			} else if (id <= 9999) {
				accountId += bankId.substring(4) + branchId.substring(4) + "000" + id;
			}else if (id <= 99999) {
				accountId += bankId.substring(4) + branchId.substring(4) + "00" + id;
			}else if (id <= 999999) {
				accountId += bankId.substring(4) + branchId.substring(4) + "0" + id;
			} else
				accountId += bankId.substring(4) + branchId.substring(4) + id;
		} else
			accountId = bankId.substring(4) + branchId.substring(4) + "0000001";
		return accountId;
	}
}
