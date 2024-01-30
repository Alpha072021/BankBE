package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address implements Comparable<Address>, Serializable {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String addressId;
	private String addressLine;
	private String pincode;
	private String country;
	private String city;

	/**
	 * @return the addressId
	 */
	public String getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the addressLine
	 */
	public String getAddressLine() {
		return addressLine;
	}

	/**
	 * @param addressLine the addressLine to set
	 */
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}

	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	// constructors
	Address() {

	}

	/**
	 * @param addressId
	 * @param addressLine
	 * @param pincode
	 * @param country
	 * @param city
	 */
	public Address(String addressId, String addressLine, String pincode, String country, String city) {
		super();
		this.addressId = addressId;
		this.addressLine = addressLine;
		this.pincode = pincode;
		this.country = country;
		this.city = city;
	}

	@Override
	public int hashCode() {
		return Objects.hash(addressId, addressLine, city, country, pincode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(addressId, other.addressId) && Objects.equals(addressLine, other.addressLine)
				&& Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(pincode, other.pincode);
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", addressLine=" + addressLine + ", pincode=" + pincode
				+ ", country=" + country + ", city=" + city + "]";
	}

	@Override
	public int compareTo(Address o) {
		// TODO Auto-generated method stub
		return this.hashCode() - o.hashCode();
	}
}
