/**
 * 
 */
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.UpdateTimestamp;

import com.alpha.bankApp.enums.AccountType;
import com.alpha.bankApp.enums.Approval;
import com.alpha.bankApp.enums.Currency;
import com.alpha.bankApp.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * @author Dixith S N
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Class_Name", discriminatorType = DiscriminatorType.STRING)
public class Account implements Comparable<Account>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String accountNumber;
	private LocalDateTime creationDateTime;
	@UpdateTimestamp
	private LocalDateTime lastModifiedDateTime;

	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	private Double availableBalance;

	private double currentBalance;

	@Enumerated(EnumType.STRING)
	private Currency currency = Currency.INR;
	private boolean primaryAccount = true;

	@Enumerated(EnumType.STRING)
	private Approval approval = Approval.IN_PROCESS;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@ManyToOne
	private Branch branch;
	@OneToOne(cascade = CascadeType.ALL)
	private Statement statement;

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@JsonIgnore
	@ManyToOne
	private User accountHolder;

	@OneToOne(cascade = CascadeType.ALL)
	private DebitCard debitCard;

	/**
	 * @return the debitCard
	 */
	public DebitCard getDebitCard() {
		return debitCard;
	}

	/**
	 * @param debitCard the debitCard to set
	 */
	public void setDebitCard(DebitCard debitCard) {
		this.debitCard = debitCard;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the creationDateTime
	 */
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	/**
	 * @param creationDateTime the creationDateTime to set
	 */
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	/**
	 * @return the lastModifiedDateTime
	 */
	public LocalDateTime getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	/**
	 * @param lastModifiedDateTime the lastModifiedDateTime to set
	 */
	public void setLastModifiedDateTime(LocalDateTime lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the availableBalance
	 */
	public Double getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	/**
	 * @return the currentBalance
	 */
	public double getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * @return the primaryAccount
	 */
	public boolean isPrimaryAccount() {
		return primaryAccount;
	}

	/**
	 * @param primaryAccount the primaryAccount to set
	 */
	public void setPrimaryAccount(boolean primaryAccount) {
		this.primaryAccount = primaryAccount;
	}

	/**
	 * @return the approval
	 */
	public Approval getApproval() {
		return approval;
	}

	/**
	 * @param approval the approval to set
	 */
	public void setApproval(Approval approval) {
		this.approval = approval;
	}

	/**
	 * @return the accountHolder
	 */
	public User getAccountHolder() {
		return accountHolder;
	}

	/**
	 * @param accountHolder the accountHolder to set
	 */
	public void setAccountHolder(User accountHolder) {
		this.accountHolder = accountHolder;
	}

	// Constructor

	public Account() {

	}

	/**
	 * @param accountNumber
	 * @param creationDateTime
	 * @param lastModifiedDateTime
	 * @param availableBalance
	 * @param currentBalance
	 * @param currency
	 * @param primaryAccount
	 * @param accountHolder
	 * @param accountType
	 */
	public Account(String accountNumber, LocalDateTime creationDateTime, LocalDateTime lastModifiedDateTime,
			Double availableBalance, double currentBalance, Currency currency, boolean primaryAccount,
			User accountHolder) {
		super();
		this.accountNumber = accountNumber;
		this.creationDateTime = creationDateTime;
		this.lastModifiedDateTime = lastModifiedDateTime;
		this.availableBalance = availableBalance;
		this.currentBalance = currentBalance;
		this.currency = currency;
		this.primaryAccount = primaryAccount;
		this.accountHolder = accountHolder;

	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", creationDateTime=" + creationDateTime
				+ ", lastModifiedDateTime=" + lastModifiedDateTime + ", status=" + status + ", availableBalance="
				+ availableBalance + ", currentBalance=" + currentBalance + ", currency=" + currency
				+ ", primaryAccount=" + primaryAccount + ", approval=" + approval + ", accountHolder=" + accountHolder
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountHolder, accountNumber, approval, availableBalance, creationDateTime, currency,
				currentBalance, lastModifiedDateTime, primaryAccount, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountHolder, other.accountHolder) && Objects.equals(accountNumber, other.accountNumber)
				&& approval == other.approval && Objects.equals(availableBalance, other.availableBalance)
				&& Objects.equals(creationDateTime, other.creationDateTime) && currency == other.currency
				&& Double.doubleToLongBits(currentBalance) == Double.doubleToLongBits(other.currentBalance)
				&& Objects.equals(lastModifiedDateTime, other.lastModifiedDateTime)
				&& primaryAccount == other.primaryAccount && status == other.status;
	}

	@Override
	public int compareTo(Account o) {
		// TODO Auto-generated method stub
		return this.hashCode() - o.hashCode();
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	/*
	 * Introducing the "Beneficiary" field with a many-to-many relationship to
	 * efficiently store user beneficiary information.
	 */
	@ManyToMany
	private List<Beneficiary> beneficiary;

	public List<Beneficiary> getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(List<Beneficiary> beneficiary) {
		this.beneficiary = beneficiary;
	}

}