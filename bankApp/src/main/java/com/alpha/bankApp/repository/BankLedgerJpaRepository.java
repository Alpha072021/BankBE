/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.bankApp.entity.BankLedger;

/**
 * @author Vinod Raj
 */
public interface BankLedgerJpaRepository extends JpaRepository<BankLedger, Long> {

}
