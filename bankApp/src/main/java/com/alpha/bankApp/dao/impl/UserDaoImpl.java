package com.alpha.bankApp.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.AccountDao;
import com.alpha.bankApp.dao.UserDao;
import com.alpha.bankApp.entity.Account;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.repository.UserJpaRepository;
import com.alpha.bankApp.util.AccountUtil;

import jakarta.transaction.Transactional;

/**
 * @author Manoj Y
 *
 */
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private UserJpaRepository repository;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountUtil accountUtil;

	@Transactional
	@Override
	public User saveUser(User user) {
		return repository.save(user);
	}

	@Override
	public Optional<User> findUserById(String userId) {
		return repository.findById(userId);
	}

	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		return repository.findByEmailAndPassword(email, password);
	}

	@Override
	public void removeUser(User user) {
		repository.delete(user);

	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public String findLastUser() {
		return repository.findLastUserId();
	}

	@Override
	public List<User> findAllUserByBranchId(String branchId) {
		List<Account> accounts = accountDao.findAllAccountByBranchId(branchId);
		return accountUtil.getUsers(accounts);
	}

	@Override
	public String findUserProfileById(String id) {
		return repository.findUserProfileById(id, DocumentType.PROFILE);
	}

}
