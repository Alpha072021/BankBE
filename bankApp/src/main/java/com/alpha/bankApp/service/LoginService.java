package com.alpha.bankApp.service;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.LoginDto;
import com.alpha.bankApp.dto.LoginResponseDto;
import com.alpha.bankApp.util.ResponseStructure;

public interface LoginService {

	ResponseEntity<ResponseStructure<LoginResponseDto>> login(LoginDto loginInfo, String users);

}
