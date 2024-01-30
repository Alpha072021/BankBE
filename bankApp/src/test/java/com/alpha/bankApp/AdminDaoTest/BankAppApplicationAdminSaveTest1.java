package com.alpha.bankApp.AdminDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.AdminDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

@SpringBootTest
class BankAppApplicationAdminSaveTest1 {

	@Autowired
	private AdminDao adminDao;

	@Test
	void contextLoads() {
		/*
		 * Employee e = new Employee("6", "Manoj Y", 0, null, "pass@0123", null, null,
		 * null, null, Role.EMPLOYEE, null); Employee emp = adminDao.saveEmployee(e);
		 */
		try {
			if ( adminDao.setAdmin("6")  ) {
				System.out.println("set");
			}
			else 
				System.out.println("not set ");
		}
		catch ( Throwable e ) {
			System.out.println(e );
		}
		System.out.println("done..!");
	}
}