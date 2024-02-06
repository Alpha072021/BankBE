package com.alpha.bankApp.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.entity.User;

@SuppressWarnings("serial")
public class ApplicationUserDetails implements UserDetails {

	private User user;

	private Employee employee;

	public ApplicationUserDetails(User user) {
		this.user = user;
	}

	public ApplicationUserDetails(Employee employee) {
		this.employee = employee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (employee != null) {
			return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name()));

		} else {
			return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserType().name()));

		}
	}

	@Override
	public String getPassword() {
		if (user != null) {
			return user.getPassword();
		} else {
			return employee.getPassword();
		}
	}

	@Override
	public String getUsername() {
		if (user != null) {
			return user.getEmail();
		} else {
			return employee.getEmail();
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
