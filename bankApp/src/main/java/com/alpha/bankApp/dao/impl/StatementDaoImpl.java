package com.alpha.bankApp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.StatementDao;
import com.alpha.bankApp.repository.StatementRepository;

@Repository
public class StatementDaoImpl implements StatementDao {
	@Autowired
	private StatementRepository statementRepository;

}
