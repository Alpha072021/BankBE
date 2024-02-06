package com.alpha.bankApp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alpha.bankApp.dao.BankDao;
import com.alpha.bankApp.dto.BranchManagerDto;
import com.alpha.bankApp.dto.ManagingDirectorDto;
import com.alpha.bankApp.entity.Bank;
import com.alpha.bankApp.entity.Branch;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.entity.idgenerator.AddressIdGenerator;

@Component
public class EmployeeUtil {

	@Autowired
	private AddressIdGenerator generator;
	@Autowired
	private BankDao dao;

	public Employee convertEmployeeInfo(Employee employee) {
		// To lowerCare the Employee Name
		if (employee.getName() != null)
			employee.setName(employee.getName().toLowerCase());
		// To lowerCare the Employee Email
		if (employee.getEmail() != null)
			employee.setEmail(employee.getEmail().toLowerCase());
		employee.setPassword("" + employee.getPhoneNumber());
		if (employee.getDateOfJoining() == null)
			employee.setDateOfJoining(LocalDateTime.now());
		// To Inject the Address Id
		if (employee.getAddress() != null) {
			employee.getAddress().setAddressId(generator.generate());
		}
		return employee;
	}

	public ManagingDirectorDto createManaginDirector(Employee employee, Bank bank) {
		if (bank != null) {
			return new ManagingDirectorDto(employee.getEmployeeId(), employee.getName(), employee.getEmail(),
					employee.getRole(), bank.getBankId(), bank.getBankName(), employee.getAddress());
		}
		return new ManagingDirectorDto(employee.getEmployeeId(), employee.getName(), employee.getEmail(),
				employee.getRole(), null, null, null);
	}

	public ManagingDirectorDto createManaginDirector(Employee employee) {

		return new ManagingDirectorDto(employee.getEmployeeId(), employee.getName(), employee.getRole());
	}

	public List<BranchManagerDto> getBranchManagers(Bank bank) {
		List<BranchManagerDto> branchManagerDtos = new ArrayList<>();
		for (Branch branch : bank.getBranches()) {
			if (branch.getBranchManager() != null) {
				branchManagerDtos.add(getBranchManagerDto(branch));
			}
		}

		return branchManagerDtos;
	}

	public BranchManagerDto getBranchManagerDto(Branch branch) {
		return new BranchManagerDto(branch.getBranchManager().getEmployeeId(), branch.getBranchManager().getName(),
				branch.getBranchManager().getEmail(), branch.getBranchId(), branch.getBranchName(), branch.getAddress(),
				branch.getIFSC(), branch.getBranchManager().getRole());
	}

	public List<ManagingDirectorDto> createManaginDirector(List<Employee> list) {
		List<ManagingDirectorDto> directorDtos = new ArrayList<>();
		for (Employee employee : list) {
			Bank bank = dao.getBankByMdId(employee.getEmployeeId());
			directorDtos.add(createManaginDirector(employee, bank));
		}
		return directorDtos;
	}
}
