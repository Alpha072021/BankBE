package com.alpha.bankApp.branchDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dao.EmployeeDao;

@SpringBootTest
class BankAppApplicationBranchUpdateTest {

	@Autowired
	BranchDao branchDao ; 
	
	@Autowired
	EmployeeDao employeeDao ; 
	
	@Test
	void contextLoads() {
		
		var newB = branchDao.getBranch("3") ; 
		var emp  = employeeDao.getEmployeeById("2") ; 
		newB.setBranchId(null);
		newB.setBranchManager( emp );
		branchDao.updateBranch("3", newB) ;  
		System.out.println("done..!");
		
	}

}
