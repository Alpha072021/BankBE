/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Vinod Raj
 */
@Entity
@DiscriminatorValue("Deposit")
@Getter
@Setter
public class Deposit extends Transaction {

	private double amount;

}
