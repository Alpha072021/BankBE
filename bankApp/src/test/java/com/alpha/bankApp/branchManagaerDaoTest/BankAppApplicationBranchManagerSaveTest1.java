package com.alpha.bankApp.branchManagaerDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchManagerDao;
import com.alpha.bankApp.dao.EmployeeDao;

@SpringBootTest
class BankAppApplicationBranchManagerSaveTest1 {

	@Autowired
	BranchManagerDao branchManagerDao; 
	
	@Autowired
	EmployeeDao employeeDao ; 
	
	@Test
	void contextLoads() {
	//	Employee bm  = new Employee(123, "ranga", 0, null, null, null, null, null, null, null, null) ;  
	//	branchManagerDao.saveEmployee(bm); 
		try {
			if ( branchManagerDao.setBranchManager("1", "1") )
				System.out.println("set ");
			else 
				System.out.println("not set ");
		}
		catch ( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!");
	}
}