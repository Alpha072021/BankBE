package com.alpha.bankApp.managingDirectorDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.ManagingDirectorDao;

@SpringBootTest
class BankAppApplicationManagingDirectorChangeTest {

	@Autowired
	private ManagingDirectorDao managingDirectorDao; 
	
	@Test
	void contextLoads() {
		try {
			if ( managingDirectorDao.changeManagingDirector("1", "5"))
				System.out.println("changed..!!");
			else 
				System.out.println("not changed..!!");
		}
		catch( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!!");
	}

}
