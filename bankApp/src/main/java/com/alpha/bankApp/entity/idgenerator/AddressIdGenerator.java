package com.alpha.bankApp.entity.idgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.repository.AddressJpaRepository;

@Component
public class AddressIdGenerator {
	@Autowired
	private AddressJpaRepository repository;

	@Autowired
	JdbcTemplate springJdbc;

	public String generate() {
		String prefix = "bharat";
		String lastAddressId = repository.countOfAdress();
		int suffix = 0;
		if (lastAddressId != null) {
			suffix = Integer.parseInt(lastAddressId.substring(lastAddressId.length() - 4, lastAddressId.length()));
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
