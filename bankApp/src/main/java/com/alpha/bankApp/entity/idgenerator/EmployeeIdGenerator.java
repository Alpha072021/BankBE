package com.alpha.bankApp.entity.idgenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class EmployeeIdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		int suffix = 0;
		String prefix = "FF";
		String lastEmployeeId = null;
		String sql = "SELECT * FROM employee ORDER BY date_of_joining DESC LIMIT 1";
		JdbcConnectionAccess con = session.getJdbcConnectionAccess();
		try {
			Connection connection = con.obtainConnection();
			PreparedStatement pmst = connection.prepareStatement(sql);
			ResultSet result = pmst.executeQuery();
			if (result.next()) {
				lastEmployeeId = result.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (lastEmployeeId != null) {
			suffix = Integer.parseInt(lastEmployeeId.substring(lastEmployeeId.length() - 4, lastEmployeeId.length()));
		}
		if (suffix < 9) {
			prefix += "000" + ++suffix;
		} else if (suffix < 99) {
			prefix += "00" + ++suffix;
		} else if (suffix < 999) {
			prefix += "0" + ++suffix;
		} else {
			prefix += ++suffix;
		}
		return prefix;
	}
}
