package com.alpha.bankApp.branchManagaerDaoTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.BranchManagerDao;
import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;

@SpringBootTest
class BankAppApplicationGetAllBranchManagerTest {

	@Autowired
	BranchManagerDao branchManagerDao; 
	
	@Autowired
	EmployeeDao employeeDao ; 
	
	@Test
	void contextLoads() {
	//	Employee bm  = employeeDao.getEmployeeById("2") ; 
	//	branchManagerDao.saveEmployee(bm); 
		try {
			List<Employee> managers = branchManagerDao.getBranchManagers() ; 
			System.out.println("**************Branch Managers**********");
			for ( Employee e : managers ) {
				System.out.println("Name : "+ e.getName());
				System.out.println("--------------------------");
			}
		}
		catch ( Throwable e ) {
			System.out.println( e );
		}
		System.out.println("done..!");
		
	}
}