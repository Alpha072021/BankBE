/**
 * 
 */
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.alpha.bankApp.enums.Approval;
import com.alpha.bankApp.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * @author Dixith S N
 *
 */

@Entity
public class DebitCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String cardNumber;
	private String cvv;
	private LocalDateTime createdDateTime;
	private LocalDate expiryDate;
	private LocalDate issueDate;
	private LocalDate validUptoDate;

	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Enumerated(EnumType.STRING)
	private Approval approval = Approval.IN_PROCESS;
	@JsonIgnore
	@OneToOne(mappedBy = "debitCard", fetch = FetchType.LAZY)
	private Account account;

	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}

	/**
	 * @param cvv the cvv to set
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	/**
	 * @return the expiryDate
	 */
	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the issueDate
	 */
	public LocalDate getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the validUptoDate
	 */
	public LocalDate getValidUptoDate() {
		return validUptoDate;
	}

	/**
	 * @param validUptoDate the validUptoDate to set
	 */
	public void setValidUptoDate(LocalDate validUptoDate) {
		this.validUptoDate = validUptoDate;
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
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	// constructor
	public DebitCard() {

	}

	/**
	 * @param cardNumber
	 * @param cvv
	 * @param expiryDate
	 * @param issueDate
	 * @param validUptoDate
	 */
	public DebitCard(String cardNumber, String cvv, LocalDate expiryDate, LocalDate issueDate,
			LocalDate validUptoDate) {
		super();
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expiryDate = expiryDate;
		this.issueDate = issueDate;
		this.validUptoDate = validUptoDate;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
}