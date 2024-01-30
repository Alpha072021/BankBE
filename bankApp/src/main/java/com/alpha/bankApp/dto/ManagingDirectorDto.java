package com.alpha.bankApp.dto;

import com.alpha.bankApp.entity.Address;
import com.alpha.bankApp.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagingDirectorDto {
	private String managingDirectorId;
	private String name;
	private String email;
	private Role role;
	private String bankId;
	private String bankName;
	private Address addres;

	public ManagingDirectorDto(String managingDirectorId, String name, Role role) {
		super();
		this.managingDirectorId = managingDirectorId;
		this.name = name;
		this.role = role;
	}

}
