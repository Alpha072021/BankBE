/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.entity;

import java.time.LocalDateTime;

import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
public class Transaction {
	@Id
	private long transactionId;
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	private LocalDateTime createdDate;
	private LocalDateTime enedDate;
	@Enumerated(EnumType.STRING)
	private TransactionType type;

}
