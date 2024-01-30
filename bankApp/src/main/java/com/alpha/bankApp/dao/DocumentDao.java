/**
 * 
 */
package com.alpha.bankApp.dao;

import java.util.List;
import java.util.Optional;

import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.enums.Status;

/**
 * @author Dixith S N
 *
 */
public interface DocumentDao {

	// create

	Document createDocument(Document document);

	// update
	// we will call createDocument to update
	// Document updateDocument(String documentId, Document document);

	// read

	Optional<Document> getDoumentById(String documentId);

	List<Document> getDocumentByType(String docsContainerId, DocumentType documentType);

	List<Document> getDocumentsByStatus(Status status);

	List<Document> getAllDocuments();

	List<Document> getAllDocuments(String docsContainerId);

	List<Document> getAllDocuments(DocumentType documentType);

	List<Document> getAllDocuments(String docsContainerId, DocumentType documentType);

	// delete

	void deleteDocumentById(String DocumentId);

	void deleteAllDocuments(String ContainerId);

}