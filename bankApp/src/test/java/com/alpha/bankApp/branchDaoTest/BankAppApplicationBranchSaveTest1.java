package com.alpha.bankApp.branchDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.entity.Branch;

@SpringBootTest
class BankAppApplicationBranchSaveTest1 {

	@Autowired
	BranchDao branchDao ; 
	
	@Test
	void contextLoads() {
		Address a = new Address("6", "line1", "56006", "india", "bangalore"); 
		Branch branch = new Branch("3", "Kalyan nagar", "icico1", a, null, 0, null);
		branchDao.createBranch("2", branch); 
		System.out.println("done..!");
		
	}

}
