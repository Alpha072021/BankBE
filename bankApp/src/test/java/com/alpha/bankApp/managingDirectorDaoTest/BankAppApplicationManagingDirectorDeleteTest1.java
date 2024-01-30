package com.alpha.bankApp.managingDirectorDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.ManagingDirectorDao;

@SpringBootTest
class BankAppApplicationManagingDirectorDeleteTest1 {

	@Autowired
	private ManagingDirectorDao managingDirectorDao; 
	
	@Test
	void contextLoads() {
		 try {
			 if ( managingDirectorDao.removeManagingDirector("1"))
				 System.out.println("removed..!!");
			 else 
				 System.out.println("not removed ");
		 }
		 catch( Throwable e ) {
			 System.out.println(e);
		 }
		System.out.println("done..!");
	}

}
