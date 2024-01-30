package com.alpha.bankApp.util;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.entity.idgenerator.AddressIdGenerator;
import com.alpha.bankApp.entity.idgenerator.UserIdGenerator;

@Component
public class UserUtil {
	@Autowired
	private UserIdGenerator generator;
	@Autowired
	private AddressIdGenerator addressGenerator;

	public User generateUserId(String branchId, User user) {
		user.setUserId(generator.userIdGenerator(branchId));
		user.setCreatedDateAndTime(LocalDateTime.now());
		DocsContainer container = new DocsContainer();
		container.setCreatedDateTime(LocalDateTime.now());
		user.setDocsContainer(container);

		if (user.getEmail() != null) {
			user.setEmail(user.getEmail().toLowerCase());
		}
		if (user.getName() != null) {
			user.setName(user.getName().toLowerCase());
		}
		if (user.getAddress() != null) {
			user.getAddress().setAddressId(addressGenerator.generate());
		}
		if (user.getPassword() == null) {
			user.setPassword("" + user.getPhoneNumber());
		}
		return user;
	}

}
