package com.alpha.bankApp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.DocsContainerDao;
import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.repository.DocContainerJpaRepository;

@Repository
public class DocsContainerDaoImpl implements DocsContainerDao {
	@Autowired
	private DocContainerJpaRepository docRepository;

	@Override
	public DocsContainer createDocsContainerForUser(String userId) {

		return null;
	}

	@Override
	public DocsContainer createDocsContainerForEmployee(String employeeId) {
		return null;
	}

	@Override
	public DocsContainer addDocumentforUser(String userID, Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocsContainer addDocumentforEmployee(String employeeId, Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocsContainer removeDocumentFromUser(String userId, String documentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocsContainer removeDocumentFromEmployee(String employeeId, String documentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocsContainer createDocsContainer(DocsContainer container) {
		return docRepository.save(container);
	}

	public void removeDocsContainer(String docsContainerId) {
		docRepository.deleteById(docsContainerId);
	}

}
