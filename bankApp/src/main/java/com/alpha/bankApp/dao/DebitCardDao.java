package com.alpha.bankApp.dao;

import java.util.List;
import java.util.Optional;

import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.enums.Approval;
import com.alpha.bankApp.enums.Status;

public interface DebitCardDao {
	DebitCard saveDebitCard(DebitCard debitCard);

	Optional<DebitCard> findById(String cardNumber);

	List<DebitCard> findByApproval(Approval approval);

	List<DebitCard> findByStatus(Status status);

	DebitCard findByAccoutnId(String accountId);

	void removeByDebitCardById(String cardNumber);
}
