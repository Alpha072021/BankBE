package com.alpha.bankApp.entity.idgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.UserDao;

@Component
public class UserIdGenerator {
	@Autowired
	private BankDao bankDao;
	@Autowired
	private UserDao userDao;

	public String userIdGenerator(String branchId) {
		String bankId = bankDao.findBankIdByBranchId(branchId);
		String lastUserId = userDao.findLastUser();
		String userId = "";
		if (lastUserId != null) {
			int id = (Integer.parseInt(lastUserId.substring(bankId.length(), lastUserId.length())) + 1);
			if (id <= 9) {
				userId += bankId + "000" + id;
			} else if (id <= 99) {
				userId += bankId + "00" + id;
			} else if (id <= 999) {
				userId += bankId + "0" + id;
			} else
				userId += bankId + id;
		} else {
			userId = bankId + "0001";
		}
		return userId;

	}
}
