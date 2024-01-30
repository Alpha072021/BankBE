package com.alpha.bankApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.LoginDto;
import com.alpha.bankApp.dto.LoginResponseDto;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.LoginService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/version/{version}")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@Operation(summary = "Logs User into the System", description = "Authenticate by Submitting User Credentials to Obtain the Token and User Role")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucess", content = @Content(schema = @Schema(implementation = ResponseStructure.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content()),
			@ApiResponse(responseCode = "401", description = "Not Authorised"),
			@ApiResponse(responseCode = "403", description = "Access Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not An Authorized Version") })
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<LoginResponseDto>> login(
			@Parameter(description = "the version used v1", required = true) @PathVariable(name = "version") String version,
			@Parameter(description = "users are categorized as employees and customers", required = true) @RequestParam String users,
			@RequestBody LoginDto loginInfo) {
		if (version.equalsIgnoreCase("v1")) {
			return loginService.login(loginInfo, users);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
