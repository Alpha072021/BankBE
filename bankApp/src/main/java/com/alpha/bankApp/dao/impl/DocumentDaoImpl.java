package com.alpha.bankApp.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.bankApp.dao.DocumentDao;
import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.repository.DocumentJpaRepository;

@Repository
public class DocumentDaoImpl implements DocumentDao {
	@Autowired
	private DocumentJpaRepository repository;

	@Override
	public Document createDocument(Document document) {

		return repository.save(document);
	}

	@Override
	public Optional<Document> getDoumentById(String documentId) {
		return repository.findById(documentId);
	}

	@Override
	public List<Document> getDocumentByType(String docsContainerId, DocumentType documentType) {
		// TODO Auto-generated method stub
		return repository.findDocumentByType(docsContainerId, documentType);
	}

	@Override
	public List<Document> getDocumentsByStatus(Status status) {
		// TODO Auto-generated method stub
		return repository.findDocumentByStatus(status);
	}

	@Override
	public List<Document> getAllDocuments() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Document> getAllDocuments(String docsContainerId) {
		// TODO Auto-generated method stub
		return repository.findAll(docsContainerId);
	}

	@Override
	public List<Document> getAllDocuments(DocumentType documentType) {
		// TODO Auto-generated method stub
		return repository.findAll(documentType);
	}

	@Override
	public List<Document> getAllDocuments(String docsContainerId, DocumentType documentType) {
		// TODO Auto-generated method stub
		return getDocumentByType(docsContainerId, documentType);
	}

	@Override
	public void deleteDocumentById(String DocumentId) {
		// TODO Auto-generated method stub
		repository.deleteById(DocumentId);

	}

	@Override
	public void deleteAllDocuments(String ContainerId) {
		// TODO Auto-generated method stub
		

	}

}
