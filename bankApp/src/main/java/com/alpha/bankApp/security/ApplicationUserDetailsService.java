package com.alpha.bankApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.exception.InvaildCredentials;
import com.alpha.bankApp.repository.EmployeeJpaRepository;
import com.alpha.bankApp.repository.UserJpaRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeJpaRepository employeerepository;

	@Autowired
	private UserJpaRepository userReposistory;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userReposistory.findByEmail(userEmail);
		Employee employee = employeerepository.findByEmail(userEmail);
		if (user != null) {
			return createUserDetails(user);

		} else if (employee != null) {
			return createEmployeeDetails(employee);
		}

		throw new InvaildCredentials("Wrong email and password" + userEmail);
	}

	private UserDetails createUserDetails(User user) {
		return new ApplicationUserDetails(user);
	}

	private UserDetails createEmployeeDetails(Employee employee) {
		return new ApplicationUserDetails(employee);
	}

}
