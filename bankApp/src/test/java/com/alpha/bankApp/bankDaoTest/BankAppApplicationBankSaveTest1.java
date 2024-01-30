package com.alpha.bankApp.bankDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.entity.Bank;

@SpringBootTest
class BankAppApplicationBankSaveTest1 {

	@Autowired
	BankDao bankDao ; 
	
	@Test
	void contextLoads() {
		Address a1 = new Address("1", "line 1", "101", "india", "bangalore");
		Bank b1 = new Bank("1", "icici", a1 , null) ;
		bankDao.createBank(b1) ; 
		System.out.println("done..!");
	}

}
