package com.alpha.bankApp.employeeDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

@SpringBootTest
class BankAppApplicationEmployeeUpdateTest {

	@Autowired
	private EmployeeDao employeeDao; 
	
	@Test
	void contextLoads() {
		Employee e = new Employee()  ;
		e.setRole(Role.EMPLOYEE);
		e.setEmail("s@gm.com");
		employeeDao.updateEmployee("3", e) ; 
		System.out.println("done..!");
	}

}
