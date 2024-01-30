package com.alpha.bankApp.entity.idgenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class DocumentIdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		String containerId = "";
		String lastContainerId = "";
		String sql = "SELECT document_id FROM document ORDER BY creation_date_time DESC LIMIT 1";
		JdbcConnectionAccess jdbcConnectionAccess = session.getJdbcConnectionAccess();
		try {
			Connection connection = jdbcConnectionAccess.obtainConnection();
			ResultSet resultSet = connection.createStatement().executeQuery(sql);
			if (resultSet.next()) {
				lastContainerId = resultSet.getString("document_id");
			} else {
				lastContainerId = "0";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int id = Integer.parseInt(lastContainerId);
		if (id >= 1) {
			containerId += ++id;
		} else {
			containerId = "1";
		}
		return containerId;
	}
}
