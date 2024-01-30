package com.alpha.bankApp.branchManagaerDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchManagerDao;
import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;

@SpringBootTest
class BankAppApplicationBranchManagerChangeTest {

	@Autowired
	BranchManagerDao branchManagerDao; 
	
	@Autowired
	EmployeeDao employeeDao ; 
	
	@Test
	void contextLoads() {
	//	Employee bm  = employeeDao.getEmployeeById("2") ; 
	//	branchManagerDao.saveEmployee(bm); 
		try {
			if ( branchManagerDao.changeBranchManager("2", "4"))
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