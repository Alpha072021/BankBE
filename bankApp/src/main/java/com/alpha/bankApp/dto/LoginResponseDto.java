package com.alpha.bankApp.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginResponseDto {
	private String token;
	private String role;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
