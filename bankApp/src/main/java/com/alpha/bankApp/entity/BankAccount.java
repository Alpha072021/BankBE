/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bankAccountId;
	private double bankBalance;
	private double cashInFlow;
	private double cashOutFlow;
	@OneToOne
	private Bank bank;
	@OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
	private List<BankLedger> bankLegder;

}