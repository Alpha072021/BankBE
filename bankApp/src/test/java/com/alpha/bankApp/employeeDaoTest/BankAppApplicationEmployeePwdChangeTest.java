package com.alpha.bankApp.employeeDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.EmployeeDao;

@SpringBootTest
class BankAppApplicationEmployeePwdChangeTest {

	@Autowired
	private EmployeeDao employeeDao; 
	
	@Test
	void contextLoads() {
		try {
			employeeDao.changePassword("4", "pass@01", "pass@02_new");
		}
		catch( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!");
	}
}
