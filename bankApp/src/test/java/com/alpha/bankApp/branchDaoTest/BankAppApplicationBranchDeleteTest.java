package com.alpha.bankApp.branchDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchDao;

@SpringBootTest
class BankAppApplicationBranchDeleteTest {

	@Autowired
	BranchDao branchDao ; 
	
	@Test
	void contextLoads() {
		branchDao.deleteBranch("2") ; 
		System.out.println("done..!");
		
	}

}
