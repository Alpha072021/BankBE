package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.dto.UserDto;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.UserService;
import com.alpha.bankApp.util.ResponseStructure;

@RestController
@RequestMapping("api/version/{version}/users")
public class UserController {
	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<ResponseStructure<User>> saveUser(@PathVariable String version, @RequestBody User user,
			@RequestParam String branchId) {
		if (version.equalsIgnoreCase("v1"))
			return service.saveUser(user, branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getById")
	public ResponseEntity<ResponseStructure<User>> findUserById(@RequestParam String userId,
			@PathVariable String version) {
		if (version.equalsIgnoreCase("v1"))
			return service.findUserById(userId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@GetMapping("/getAllUsersByBrancId")
	public ResponseEntity<ResponseStructure<List<User>>> findAllUserByBranchId(String branchId,
			@PathVariable String version) {
		if (version.equalsIgnoreCase("v1"))
			return service.findAllUserByBranchId(branchId);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<User>> updateUser(@PathVariable String version, @RequestParam String userId,
			@RequestBody User user) {
		if (version.equalsIgnoreCase("v1"))
			return service.updateUser(userId, user);
		throw new VersionUnauthorizedException("Not An Authorized Version");

	}

	@GetMapping("/getUserProfile")
	public ResponseEntity<ResponseStructure<UserDto>> getUserProfile(@PathVariable String version,
			@RequestHeader("Authorization") String token) {
		if (version.equalsIgnoreCase("v1"))
			return service.getUserProfile(token);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

}
