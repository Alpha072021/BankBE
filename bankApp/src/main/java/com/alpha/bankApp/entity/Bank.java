package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Bank implements Comparable<Bank>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "bankId", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "bankId", strategy = "com.alpha.bankApp.entity.idgenerator.BankIdGenerator")
	private String bankId;
	private String bankName;
	@JsonIgnore
	private LocalDateTime bankCreated;

	/*
	 * Unidirectional one-to-one mapping with Address
	 */

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	/*
	 * Unidirectional one-to-one mapping with Employee
	 */
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Employee managingDirector;

	/*
	 * Unidirectional one-to-Many mapping with Branch
	 */
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Branch> branches;

	/**
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankCreatedDateAndTime
	 */
	public LocalDateTime getBankCreated() {
		return bankCreated;
	}

	/**
	 * @param LocalDateTime the CreationTime to set
	 */
	public void setBankCreated(LocalDateTime bankCreated) {
		this.bankCreated = bankCreated;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the managindDirector
	 */
	public Employee getManagingDirector() {
		return managingDirector;
	}

	/**
	 * @param managindDirector the managindDirector to set
	 */
	public void setManagingDirector(Employee managingDirector) {
		this.managingDirector = managingDirector;
	}

	/**
	 * @return the branches
	 */
	public List<Branch> getBranches() {
		return branches;
	}

	/**
	 * @param branches the branches to set
	 */
	public void setBranches(List<Branch> branches) {
//		if ( branches == null ) {
//			this.branches = new ArrayList<>() ;
//		}
		this.branches = branches;
	}

	/**
	 * @param branches the branches to set
	 */
	/*
	 * public void setBranches(Branch branch) { this.branches.add(branch); }
	 */

	// constructors
	public Bank() {

	}

	/**
	 * @param bankId
	 * @param bankName
	 * @param address
	 * @param managindDirector
	 * @param branches
	 */
	public Bank(String bankId, String bankName, Address address, Employee managindDirector) {
		super();
		this.bankId = bankId;
		this.bankName = bankName;
		this.address = address;
		this.managingDirector = managindDirector;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, bankId, bankName, branches, managingDirector);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bank other = (Bank) obj;
		return Objects.equals(address, other.address) && Objects.equals(bankId, other.bankId)
				&& Objects.equals(bankName, other.bankName) && Objects.equals(branches, other.branches)
				&& Objects.equals(managingDirector, other.managingDirector);
	}

	@Override
	public int compareTo(Bank o) {
		// TODO Auto-generated method stub
		return this.hashCode() - o.hashCode();
	}

	@Override
	public String toString() {
		return "Bank [bankId=" + bankId + ", bankName=" + bankName + ", address=" + address + ", managingDirector="
				+ managingDirector + ", branches=" + branches + "]";
	}
}