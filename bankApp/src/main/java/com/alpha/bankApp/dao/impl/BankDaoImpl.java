package com.alpha.bankApp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.exception.BranchNotFoundException;
import com.alpha.bankApp.repository.BankJpaRepository;

@Repository
public class BankDaoImpl implements BankDao {

	@Autowired
	JdbcTemplate springJdbc;

	@Autowired
	private BankJpaRepository repository;

	@Override
	public Bank createBank(Bank bank) {
		return repository.save(bank);
	}

	@Override
	public Bank updateBank(String bankId, Bank bank) {
		var result = repository.findById(bankId);
		if (result.isPresent()) {
			bank.setBankId(bankId);
			return repository.save(bank);
		}
		return null;
	}

	@Override
	public Bank getBank(String bankId) {
		var res = repository.findById(bankId);
		return res.isPresent() ? res.get() : null;
	}

	// To Get the Bank Details Based on the ManaginDirector Id
	@Override
	public Bank getBankByMdId(String employeeId) {
		var res = repository.findByMdId(employeeId);
		return res;
	}

	@Override
	public Bank deleteBank(String bankId) {
		Bank res = getBank(bankId);
		if (res != null)
			repository.delete(res);
		return res;
	}

	@Override
	public List<Bank> getBankByName(String bankName) {
		return repository.findByBankName(bankName);
	}

	public int mapBranch(String bankId, String branchId) {
		String querry = """
				insert into bank_branches (bank_bank_id, branches_branch_id )
				values(?,?);
				""";
		return springJdbc.update(querry, bankId, branchId);
	}

	// to fetch All the Bank
	@Override
	public List<Bank> getAllBank() {
		return repository.findAll();
	}

	@Override
	public List<Bank> getAllBanks() {

		return repository.getAllBanks();
	}

	@Override
	public String findBankIdByBranchId(String branchId) {

		String sql = "SELECT * FROM bank_branches WHERE branches_branch_id ='" + branchId + "';";

		// Define a RowMapper to map the result set to the bankId
		RowMapper<String> rowMapper = (resultSet, rowNum) -> resultSet.getString(1);

		List<String> result = springJdbc.query(sql, rowMapper);
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		throw new BranchNotFoundException("Branch Does Not Belongs to Any Bank");
	}

}
