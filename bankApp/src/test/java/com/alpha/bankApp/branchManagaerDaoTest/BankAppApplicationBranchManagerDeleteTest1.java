package com.alpha.bankApp.branchManagaerDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchManagerDao;

@SpringBootTest
class BankAppApplicationBranchManagerDeleteTest1 {

	@Autowired
	BranchManagerDao branchManagerDao ; ; 
	
	@Test
	void contextLoads() {
		
		try {
			if ( branchManagerDao.removeBranchManager("3")) {
				System.out.println("removed ");
			}
			else 
				System.out.println("not removed ");
		}
		catch( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!");
	}
}