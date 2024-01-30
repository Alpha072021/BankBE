/**
 * 
 */
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.alpha.bankApp.enums.EducationalQualification;
import com.alpha.bankApp.enums.MaritalStatus;
import com.alpha.bankApp.enums.OccupationType;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.enums.UserType;

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
@Entity(name = "bank_user")
public class User implements Comparable<User>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// properties or fields
	@Id
	private String userId;
	private String name;
	private long phoneNumber;
	private String email;
	private String gender;
	private String password;
	private LocalDate dateOfBirth;
	private LocalDateTime createdDateAndTime;
	private String token;
	@Enumerated(EnumType.STRING)
	private EducationalQualification qualification;
	@Enumerated(EnumType.STRING)
	private OccupationType occupationType;
	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;
	private double annualIncome;
	private String fatherName;
	private String motherName;

	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Enumerated(EnumType.STRING)
	private UserType userType = UserType.NON_ACCOUNT_HOLDER;

	@OneToOne
	@Cascade(CascadeType.ALL)
	private DocsContainer docsContainer;

	@OneToOne
	@Cascade(CascadeType.ALL)
	private Address address;

	@OneToMany(mappedBy = "accountHolder", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private List<Account> accounts;

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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phoneNumber
	 */
	public long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the docsContainer
	 */
	public DocsContainer getDocsContainer() {
		return docsContainer;
	}

	/**
	 * @param docsContainer the docsContainer to set
	 */
	public void setDocsContainer(DocsContainer docsContainer) {
		this.docsContainer = docsContainer;
	}

	public EducationalQualification getQualification() {
		return qualification;
	}

	public void setQualification(EducationalQualification qualification) {
		this.qualification = qualification;
	}

	public OccupationType getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(OccupationType occupationType) {
		this.occupationType = occupationType;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public double getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	// constructor
	public User() {

	}

	/**
	 * @param userId
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param gender
	 * @param password
	 * @param dateOfBirth
	 * @param token
	 */
	public User(String userId, String name, long phoneNumber, String email, String gender, String password,
			LocalDate dateOfBirth, String token) {
		super();
		this.userId = userId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.gender = gender;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.token = token;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", gender=" + gender + ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", token=" + token
				+ ", qualification=" + qualification + ", occupationType=" + occupationType + ", maritalStatus="
				+ maritalStatus + ", annualIncome=" + annualIncome + ", fatherName=" + fatherName + ", motherName="
				+ motherName + ", status=" + status + ", userType=" + userType + ", docsContainer=" + docsContainer
				+ ", address=" + address + ", accounts=" + accounts + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accounts, address, annualIncome, dateOfBirth, docsContainer, email, fatherName, gender,
				maritalStatus, motherName, name, occupationType, password, phoneNumber, qualification, status, token,
				userId, userType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(accounts, other.accounts) && Objects.equals(address, other.address)
				&& Double.doubleToLongBits(annualIncome) == Double.doubleToLongBits(other.annualIncome)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(docsContainer, other.docsContainer)
				&& Objects.equals(email, other.email) && Objects.equals(fatherName, other.fatherName)
				&& Objects.equals(gender, other.gender) && maritalStatus == other.maritalStatus
				&& Objects.equals(motherName, other.motherName) && Objects.equals(name, other.name)
				&& occupationType == other.occupationType && Objects.equals(password, other.password)
				&& phoneNumber == other.phoneNumber && qualification == other.qualification && status == other.status
				&& Objects.equals(token, other.token) && Objects.equals(userId, other.userId)
				&& userType == other.userType;
	}

	@Override
	public int compareTo(User o) {
		return this.hashCode() - o.hashCode();
	}

	public LocalDateTime getCreatedDateAndTime() {
		return createdDateAndTime;
	}

	public void setCreatedDateAndTime(LocalDateTime createdDateAndTime) {
		this.createdDateAndTime = createdDateAndTime;
	}
}