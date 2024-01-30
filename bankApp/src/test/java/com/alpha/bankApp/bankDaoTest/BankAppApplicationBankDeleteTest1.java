package com.alpha.bankApp.bankDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BankDao;

@SpringBootTest
class BankAppApplicationBankDeleteTest1 {

	@Autowired
	BankDao bankDao ; 
	
	@Test
	void contextLoads() {
		
		bankDao.deleteBank("1") ;  
		System.out.println("done..!");
	}
}