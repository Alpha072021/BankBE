package com.alpha.bankApp.employeeDaoTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

@SpringBootTest
class BankAppApplicationEmployeeSaveTest1 {

	@Autowired
	private EmployeeDao employeeDao;

	@Test
	void contextLoads() {
		Employee e = new Employee("1", "Nandan Neelakani", 0, null, "pass@0123", null, null, null, null,
				Role.MANAGING_DIRECTOR, null);
		employeeDao.saveEmployee(e);
		System.out.println("done..!");
	}

}
