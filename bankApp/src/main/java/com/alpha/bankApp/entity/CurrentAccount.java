/**
 * 
 */
package com.alpha.bankApp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * @author Dixith S N
 *
 */
@Entity
@DiscriminatorValue("CurrentAccount")
public class CurrentAccount extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double currentIntrestRate;

	/**
	 * @return the currentIntrestRate
	 */
	public double getCurrentIntrestRate() {
		return currentIntrestRate;
	}

	/**
	 * @param currentIntrestRate the currentIntrestRate to set
	 */
	public void setCurrentIntrestRate(double currentIntrestRate) {
		this.currentIntrestRate = currentIntrestRate;
	}

}
