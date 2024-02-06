/**
 * 
 */
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.alpha.bankApp.enums.BranchType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * @author Dixith S N
 *
 */
@Entity
public class Branch implements Comparable<Branch>, Serializable {
	/**
	 *   
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String branchId;
	private String branchName;
	private String IFSC;
	private LocalDateTime creationDateAndTime;
	/*
	 * Unidirectional one-to-one mapping with Address
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	private String branchEmail;
	private long branchPhoneNumber;
	@Enumerated(EnumType.STRING)
	private BranchType branchType;
	/*
	 * Unidirectional one-to-one mapping with Employee
	 */
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private Employee branchManager;
	/*
	 * Adjusted the CascadeType to eliminate the association with the bank or
	 * branch, ensuring that when removing the bank or branch, the corresponding
	 * account linked to the bank or branch will also be removed.
	 */
	@OneToMany(mappedBy = "branch", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
	@JsonIgnore
	private List<Account> accounts;

	/*
	 * Further Update
	 */
//	
//	private List<CreditCards> creditCards ; 

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @return the accounts
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	 * @return the branchEmail
	 */
	public String getBranchEmail() {
		return branchEmail;
	}

	/**
	 * @param branchEmail the branchEmail to set
	 */
	public void setBranchEmail(String branchEmail) {
		this.branchEmail = branchEmail;
	}

	/**
	 * @return the branchPhoneNumber
	 */
	public long getBranchPhoneNumber() {
		return branchPhoneNumber;
	}

	/**
	 * @param branchPhoneNumber the branchPhoneNumber to set
	 */
	public void setBranchPhoneNumber(long branchPhoneNumber) {
		this.branchPhoneNumber = branchPhoneNumber;
	}

	/**
	 * @return the branchManager
	 */
	public Employee getBranchManager() {
		return branchManager;
	}

	/**
	 * @param branchManager the branchManager to set
	 */
	public void setBranchManager(Employee branchManager) {
		this.branchManager = branchManager;
	}

	/**
	 * @return the iFSC
	 */
	public String getIFSC() {
		return IFSC;
	}

	/**
	 * @param IFSC the IFSC to set
	 */
	public void setIFSC(String iFSC) {
		IFSC = iFSC;
	}

	public LocalDateTime getCreationDateAndTime() {
		return creationDateAndTime;
	}

	public void setCreationDateAndTime(LocalDateTime creationDateAndTime) {
		this.creationDateAndTime = creationDateAndTime;
	}

	// constructors
	public Branch() {

	}

	/**
	 * @param branchId
	 * @param branchName
	 * @param iFSC
	 * @param address
	 * @param branchEmail
	 * @param branchPhoneNumber
	 * @param branchManager
	 */
	public Branch(String branchId, String branchName, String iFSC, Address address, String branchEmail,
			long branchPhoneNumber, Employee branchManager) {
		super();
		this.branchId = branchId;
		this.branchName = branchName;
		IFSC = iFSC;
		this.address = address;
		this.branchEmail = branchEmail;
		this.branchPhoneNumber = branchPhoneNumber;
		this.branchManager = branchManager;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IFSC, address, branchEmail, branchId, branchManager, branchName, branchPhoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		return Objects.equals(IFSC, other.IFSC) && Objects.equals(address, other.address)
				&& Objects.equals(branchEmail, other.branchEmail) && Objects.equals(branchId, other.branchId)
				&& Objects.equals(branchManager, other.branchManager) && Objects.equals(branchName, other.branchName)
				&& branchPhoneNumber == other.branchPhoneNumber;
	}

	@Override
	public String toString() {
		return "Branch [branchId=" + branchId + ", branchName=" + branchName + ", IFSC=" + IFSC + ", address=" + address
				+ ", branchEmail=" + branchEmail + ", branchPhoneNumber=" + branchPhoneNumber + ", branchManager="
				+ branchManager + "]";
	}

	@Override
	public int compareTo(Branch o) {
		return this.hashCode() - o.hashCode();
	}

	public BranchType getBranchType() {
		return branchType;
	}

	public void setBranchType(BranchType branchType) {
		this.branchType = branchType;
	}

}
