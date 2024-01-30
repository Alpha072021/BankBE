package com.alpha.bankApp.AdminDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.AdminDao;

@SpringBootTest
class BankAppApplicationAdminDeleteTest {

	@Autowired
	private AdminDao adminDao; 
	
	@Test
	void contextLoads() {
		if ( adminDao.removeAdmin("6") ) {
			System.out.println("removed");
		}
		else 
			System.out.println("not removed"); 
		System.out.println("done..!");
	}
}