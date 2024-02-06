/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.alpha.bankApp.enums.TransactionStatus;
import com.alpha.bankApp.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transactionId;
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
	@CreationTimestamp
	private LocalDateTime createdDate;
	private LocalDateTime enedDate;
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	@ManyToOne
	@JsonIgnore
	private Statement statement;

}
