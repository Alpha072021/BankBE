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
@DiscriminatorValue("SavingsAccount")
public class SavingsAccount extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
