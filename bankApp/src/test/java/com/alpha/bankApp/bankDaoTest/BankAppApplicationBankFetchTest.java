package com.alpha.bankApp.bankDaoTest;

import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.entity.Branch;

import jakarta.transaction.Transactional;

@SpringBootTest
class BankAppApplicationBankFetchTest {

	@Autowired
	BankDao bankDao ; 
	
	@Test
	@Transactional
	void contextLoads() {
		var bank  = bankDao.getBank("1") ; 
		System.out.println(bank);
		System.out.println("+++++++++++++ Branches +++++++++++");
		List<Branch> branches = null ;
		try {
			Hibernate.initialize(bank.getBranches());
			branches = bank.getBranches() ; 
			boolean state = Hibernate.isInitialized(branches) ; 
			System.out.println(state);
			int size  = branches.size() ; 
			System.out.println(size);
		}
		catch(Throwable e ) {
			System.out.println( e );
		}
		for ( int i = 0 ; i < branches.size() ; i++ ) {
			System.out.println(branches.get(i));
			System.out.println("==============");
		}
		System.out.println("done..!");
	}
}