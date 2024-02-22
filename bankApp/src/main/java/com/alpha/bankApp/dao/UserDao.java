package com.alpha.bankApp.dao;

import java.util.List;
import java.util.Optional;

import com.alpha.bankApp.entity.User;

/**
 * @author Manoj Y
 *
 */
public interface UserDao {

	User saveUser(User user);

	Optional<User> findUserById(String userId);

	User findUserByEmailAndPassword(String email, String password);

	void removeUser(User user);

	List<User> findAll();

	String findLastUser();

	List<User> findAllUserByBranchId(String branchId);

	/*
	 * This method is specifically designed to retrieve Customer profiles based on
	 * their user IDs.
	 */
	String findUserProfileById(String id);
}
