package com.alpha.bankApp.managingDirectorDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.ManagingDirectorDao;
import com.alpha.bankApp.entity.Employee;

@SpringBootTest
class BankAppApplicationManagingDirectorSetTest {

	@Autowired
	private ManagingDirectorDao managingDirectorDao;

	@Test
	void contextLoads() {
		Employee bm  = new Employee("123", "ranga", 123l, null, null, null, null, null);   
		bm = managingDirectorDao.saveEmployee(bm) ; 
		try {
			if (bm != null && managingDirectorDao.setManagingDirector("1", "123"))
				System.out.println("set..!!");
			else
				System.out.println("not set ");
		} catch (Throwable e) {
			System.out.println(e);
		}
		System.out.println("done...1!");
	}

}
