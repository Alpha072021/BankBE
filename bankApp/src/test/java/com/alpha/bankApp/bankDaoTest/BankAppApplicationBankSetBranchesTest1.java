package com.alpha.bankApp.bankDaoTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;

@SpringBootTest
class BankAppApplicationBankSetBranchesTest1 {

	@Autowired
	private BankDao bankDao ; 
	
	@Autowired
	private BranchDao branchDao ; 
	
	@Test
	void contextLoads() {
		List<Branch> branches = branchDao.getBranches() ; 
		
		Bank b1 = bankDao.getBank("1") ; 
		b1.setBranches(branches);
		bankDao.updateBank("1", b1) ; 
		System.out.println("done..!");
	}

}
