
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import com.alpha.bankApp.enums.Role;
import com.alpha.bankApp.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * @author Dixith S N
 *
 */
@Entity
public class Employee implements Comparable<Employee>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "customId", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "customId", strategy = "com.alpha.bankApp.entity.idgenerator.EmployeeIdGenerator")
	private String employeeId;
	private String name;
	private long phoneNumber;
	private String email;
	@JsonIgnore
	private String password;
	private String gender;
	private Date dateOfBirth;
	private LocalDateTime dateOfJoining;
	private String token;
	@Enumerated(EnumType.STRING)
	private Role role = Role.EMPLOYEE;
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	private Address address;

	@OneToOne
	private DocsContainer docsContainer;

	/*
	 * further enhancement
	 */
	// private Docs docs ;

	// constructors

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

	public Employee() {

	}

	/**
	 * @return the employeeID
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeID the employeeID to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the dateOfJoining
	 */
	public LocalDateTime getDateOfJoining() {
		return dateOfJoining;
	}

	/**
	 * @param dateOfJoining the dateOfJoining to set
	 */
	public void setDateOfJoining(LocalDateTime dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
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
	 * @param employeeID
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param password
	 * @param gender
	 * @param dateOfBirth
	 * @param dateOfJoining
	 * @param token
	 * @param role
	 * @param status
	 */
	public Employee(String employeeID, String name, long phoneNumber, String email, String password, String gender,
			Date dateOfBirth, LocalDateTime dateOfJoining, String token, Role role, Status status) {
		super();
		this.employeeId = employeeID;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.dateOfJoining = dateOfJoining;
		this.token = token;
		this.role = role;
		this.status = status;
	}

	public Employee(String employeeID, String name, long phoneNumber, String email, String password, String gender,
			Date dateOfBirth, LocalDateTime dateOfJoining) {
		super();
		this.employeeId = employeeID;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.dateOfJoining = dateOfJoining;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, dateOfJoining, email, employeeId, gender, name, password, phoneNumber, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(dateOfJoining, other.dateOfJoining)
				&& Objects.equals(email, other.email) && Objects.equals(employeeId, other.employeeId)
				&& Objects.equals(gender, other.gender) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && phoneNumber == other.phoneNumber && role == other.role;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeId + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email="
				+ email + ", password=" + password + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth
				+ ", dateOfJoining=" + dateOfJoining + ", role= " + role + "]";
	}

	/*
	 * Comparable
	 */
	@Override
	public int compareTo(Employee o) {
		return this.hashCode() - o.hashCode();
	}
}
