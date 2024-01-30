/**
 * 
 */
package com.alpha.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.enums.Role;

import jakarta.transaction.Transactional;

/**
 * @author Dixith S N
 *
 */
public interface EmployeeJpaRepository extends JpaRepository<Employee, String> {

	@Query("select e from Employee e where e.email = ?1  and e.password = ?2")
	Employee findByEmailAndPwd(String email, String password);

	@Modifying(clearAutomatically = true )
	@Transactional
	@Query("update Employee e set e.password = :newPassword where e.employeeId = :employeeId and e.password = :oldPassword ")
	void changePassword(@Param(value="employeeId") String employeeId,@Param(value="oldPassword") String oldPassword, @Param(value="newPassword") String newPassword);

	@Modifying(clearAutomatically = true )
	@Transactional
	@Query("update Employee e set e.name = :name where e.employeeId = :employeeId ")
	int changeName(@Param(value = "employeeId") String employeeId ,@Param(value = "name") String name ) ;
	
	
	/**
	 * @param employeeId
	 * @param role
	 * @return
	 */
	@Modifying(clearAutomatically = true )
	@Transactional
	@Query("update Employee e set e.role=:role where e.employeeId = :employeeId")
	int changeRole(@Param(value="employeeId") String employeeId,@Param(value="role") Role role);

	
	
	Employee findByEmail(String email);

	Employee findByName(String name);

	@Modifying
	@Query("delete from Employee e where e.employeeId = ?1")
	void deleteById(String employeeId);

	List<Employee> getEmployeeByRole(Role role);	
}