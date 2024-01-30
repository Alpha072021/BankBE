package com.alpha.bankApp.employeeDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

@SpringBootTest
class BankAppApplicationEmployeeDeleteTest {

	@Autowired
	private EmployeeDao employeeDao; 
	
	@Test
	void contextLoads() {
		employeeDao.deleteById("3");
		System.out.println("done..!");
	}
}