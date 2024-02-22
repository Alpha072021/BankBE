package com.alpha.bankApp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.bankApp.dao.BranchDao;
import com.alpha.bankApp.dao.UserDao;
import com.alpha.bankApp.dto.UserDto;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.exception.UserNotFoundException;
import com.alpha.bankApp.repository.BankJpaRepository;
import com.alpha.bankApp.security.JWTUtils;
import com.alpha.bankApp.service.UserService;
import com.alpha.bankApp.util.AccountUtil;
import com.alpha.bankApp.util.ResponseStructure;
import com.alpha.bankApp.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Manoj Y
 *
 */

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao dao;
	@Autowired
	private UserUtil util;
	@Autowired
	private AccountUtil accountUtil;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private BankJpaRepository bankJpaRepository;

//To save the User Details
	@Override
	public ResponseEntity<ResponseStructure<User>> saveUser(User user, String branchId) {
		Branch branch = branchDao.getBranch(branchId);
		if (branch != null) {
			ResponseStructure<User> structure = new ResponseStructure<>();
			user = (util.generateUserId(branchId, user));
			// Creating Account Number and DebitCard Number
			user.setAccounts(accountUtil.createAccountId(user, branchId));
			structure.setMessage("Created");
			structure.setStatusCode(HttpStatus.CREATED.value());
			structure.setData(dao.saveUser(user));
			return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.CREATED);
		}
		throw new BranchNotFoundException("Branch With the Given Id Not Exist");
	}

	@Override
	public ResponseEntity<ResponseStructure<User>> findUserById(String userId) {
		Optional<User> optional = dao.findUserById(userId);
		if (optional.isPresent()) {
			ResponseStructure<User> structure = new ResponseStructure<>();
			structure.setData(optional.get());
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
		}
		throw new UserNotFoundException("Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> removeUser(String userId) {
		Optional<User> optional = dao.findUserById(userId);
		if (optional.isPresent()) {
			dao.removeUser(optional.get());
			ResponseStructure<String> structure = new ResponseStructure<>();
			structure.setData("Removed");
			structure.setMessage("Removed");
			structure.setStatusCode(HttpStatus.NO_CONTENT.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NO_CONTENT);
		}
		throw new UserNotFoundException("Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<User>>> findAllUserByBranchId(String branchId) {
		List<User> users = dao.findAllUserByBranchId(branchId);
		if (users != null && !users.isEmpty()) {
			ResponseStructure<List<User>> structure = new ResponseStructure<>();
			structure.setData(users);
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<User>>>(structure, HttpStatus.OK);
		}
		throw new BranchNotFoundException("Branch Not Have Any User Accounts!!");
	}

	@Override
	public ResponseEntity<ResponseStructure<User>> updateUser(String userId, User user) {
		Optional<User> optional = dao.findUserById(userId);
		if (optional.isPresent()) {
			ResponseStructure<User> structure = new ResponseStructure<>();
			structure.setData(dao.saveUser(user));
			structure.setMessage("Modified");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<User>>(structure, HttpStatus.OK);
		}
		throw new UserNotFoundException("User With the Given Id " + userId + " Not Found");
	}

	@Override
	public ResponseEntity<ResponseStructure<UserDto>> getUserProfile(String token) {
		token = token.substring(7);
		String userId = (String) jwtUtils.extractAllClaims(token).get("userId");
		Optional<User> optional = dao.findUserById(userId);
		if (optional.isPresent()) {

			UserDto userDto = UserDto.builder().userId(optional.get().getUserId()).name(optional.get().getName())
					.phoneNumber(optional.get().getPhoneNumber()).email(optional.get().getEmail())
					.gender(optional.get().getGender()).token(optional.get().getToken())
					.qualification(optional.get().getQualification()).occupationType(optional.get().getOccupationType())
					.status(optional.get().getStatus()).userType(optional.get().getUserType())
					.address(optional.get().getAddress()).accounts(optional.get().getAccounts()).build();
			userDto.setBank(bankJpaRepository
					.findBankByBranchId(optional.get().getAccounts().get(0).getBranch().getBranchId()));
			ResponseStructure<UserDto> structure = new ResponseStructure<>();
			structure.setData(userDto);
			structure.setMessage("Found");
			structure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<UserDto>>(structure, HttpStatus.OK);
		}
		throw new UserNotFoundException("Not An Authorized User");
	}
}
