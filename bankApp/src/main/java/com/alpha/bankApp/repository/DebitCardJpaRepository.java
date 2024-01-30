/**
 * 
 */
package com.alpha.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.enums.Approval;
import com.alpha.bankApp.enums.Status;

/**
 * @author Dixith S N
 *
 */
public interface DebitCardJpaRepository extends JpaRepository<DebitCard, String> {

	List<DebitCard> findByApproval(Approval approval);

	List<DebitCard> findByStatus(Status status);

	@Query("SELECT d FROM DebitCard d WHERE d.account.accountNumber=?1")
	DebitCard findByAccoutnId(String accountId);

}
