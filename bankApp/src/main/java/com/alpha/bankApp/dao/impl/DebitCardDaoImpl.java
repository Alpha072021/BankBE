package com.alpha.bankApp.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.DebitCardDao;
import com.alpha.bankApp.entity.DebitCard;
import com.alpha.bankApp.enums.Approval;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.repository.DebitCardJpaRepository;

@Repository
public class DebitCardDaoImpl implements DebitCardDao {
	@Autowired
	private DebitCardJpaRepository repository;

	@Override
	public DebitCard saveDebitCard(DebitCard debitCard) {
		// Save the DebitCard
		return repository.save(debitCard);
	}

	@Override
	public Optional<DebitCard> findById(String cardNumber) {
		// TODO Auto-generated method stub
		return repository.findById(cardNumber);
	}

	@Override
	public List<DebitCard> findByApproval(Approval approval) {
		// TODO Auto-generated method stub
		return repository.findByApproval(approval);
	}

	@Override
	public List<DebitCard> findByStatus(Status status) {
		// TODO Auto-generated method stub
		return repository.findByStatus(status);
	}

	@Override
	public DebitCard findByAccoutnId(String accountId) {
		// TODO Auto-generated method stub
		return repository.findByAccoutnId(accountId);
	}

	@Override
	public void removeByDebitCardById(String cardNumber) {
		// TODO Auto-generated method stub
		repository.deleteById(cardNumber);
	}

}
