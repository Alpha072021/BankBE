/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Vinod Raj
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankLedger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bankLedgerId;
	private LocalDateTime date;
	private double cashInFlow;
	private double cashOutFlow;
	private double openingBalance;
	private double closingBalance;
	@ManyToOne
	private BankAccount bankAccount;

}
