/**
 *  @author Vinod Raj
 */
package com.alpha.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.bankApp.entity.BankAccount;

/**
 * @author Vinod Raj
 */
public interface BankAccountJpaRepository extends JpaRepository<BankAccount, Long> {

}
