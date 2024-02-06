package com.alpha.bankApp.entity.idgenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.exception.InvalidBankNameException;

@SuppressWarnings("serial")
@Component
public class BankIdGenerator implements IdentifierGenerator {
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		Bank bank = (Bank) object;
		String prefix = null;
		try {
			prefix = bank.getBankName().substring(0, 4);
		} catch (Exception e) {
			throw new InvalidBankNameException("Bank Name Should Exceed 4 Characters");
		}
		String sufix = "";
		String lastId = null;
		String sql = "SELECT * FROM bank ORDER BY bank_created DESC LIMIT 1";

		JdbcConnectionAccess jdbcConnectionAccess = session.getJdbcConnectionAccess();
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = jdbcConnectionAccess.obtainConnection();
			resultSet = connection.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				lastId = resultSet.getString("bank_id");
			} else {
				sufix = "0001";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.err.println(e);
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.err.println(e);
				}
			}
		}
		if (lastId == null) {
			sufix = "0001";
		} else {
			int oldNumId = Integer.parseInt(lastId.substring(4, lastId.length()));
			if (oldNumId < 9) {
				sufix = "000" + ++oldNumId;
			} else if (oldNumId < 99) {
				sufix = "00" + ++oldNumId;
			} else if (oldNumId < 999) {
				sufix = "0" + ++oldNumId;
			} else
				sufix = "" + ++oldNumId;
		}
		return prefix + sufix;

	}
}
