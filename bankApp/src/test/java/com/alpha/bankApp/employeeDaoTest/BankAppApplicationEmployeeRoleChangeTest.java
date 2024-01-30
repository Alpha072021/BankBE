package com.alpha.bankApp.employeeDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.enums.Role;

import jakarta.transaction.Transactional;

@SpringBootTest
class BankAppApplicationEmployeeRoleChangeTest {

	@Autowired
	private EmployeeDao employeeDao; 
	
	@Test
	void contextLoads() {
		try {
			employeeDao.changeRole("2", Role.BRANCH_MANAGER); 
		}
		catch( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!");
	}
}
