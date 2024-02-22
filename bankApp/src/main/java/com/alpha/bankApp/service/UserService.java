package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.alpha.bankApp.dto.UserDto;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.util.ResponseStructure;

/**
 * @author Manoj Y
 *
 */
public interface UserService {
	ResponseEntity<ResponseStructure<User>> saveUser(User user, String branchId);

	ResponseEntity<ResponseStructure<User>> findUserById(String userId);

	ResponseEntity<ResponseStructure<String>> removeUser(String userId);

	ResponseEntity<ResponseStructure<List<User>>> findAllUserByBranchId(String branchId);

	ResponseEntity<ResponseStructure<User>> updateUser(String userId, User user);

	ResponseEntity<ResponseStructure<UserDto>> getUserProfile(String token);
}
