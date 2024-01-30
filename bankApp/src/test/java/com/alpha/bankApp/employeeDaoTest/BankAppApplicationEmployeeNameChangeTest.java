package com.alpha.bankApp.employeeDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.EmployeeDao;

@SpringBootTest
class BankAppApplicationEmployeeNameChangeTest {

	@Autowired
	private EmployeeDao employeeDao; 
	
	@Test
	void contextLoads() {
		try {
			System.out.println(employeeDao.changeName("5", "Dr. brutt"));
		}
		catch( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!");
	}

}
