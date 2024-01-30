package com.alpha.bankApp.branchDaoTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.entity.Branch;

@SpringBootTest
class BankAppApplicationBankgetBranches {


	@Autowired
	private BranchDao branchDao ; 
	
	@Test
	void contextLoads() {
		List<Branch> branches = branchDao.getBranches() ; 	
		System.out.println("************ Branches *********");
		for( Branch b : branches ) {
			System.out.println("branch Name: " + b.getBranchName());
			System.out.println("Address: " + b.getAddress());
			System.out.println("==================================");
		}
		System.out.println("*******************************");
		System.out.println("done..!");
	}
}
