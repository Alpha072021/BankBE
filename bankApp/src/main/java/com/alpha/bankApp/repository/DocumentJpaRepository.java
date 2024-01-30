/**
 * 
 */
package com.alpha.bankApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.enums.Status;

/**
 * @author Dixith S N
 *
 */
public interface DocumentJpaRepository extends JpaRepository<Document, String> {
	@Query("SELECT d FROM Document d WHERE d.docsContainer.docsContainerId=?1 AND d.type=?2")
	List<Document> findDocumentByType(String docsContainerId, DocumentType documentType);

	List<Document> findDocumentByStatus(Status status);

	@Query("SELECT d FROM Document d WHERE d.docsContainer.docsContainerId=?1 ")
	List<Document> findAll(String docsContainerId);

	@Query("SELECT d FROM Document d WHERE d.type=?1")
	List<Document> findAll(DocumentType documentType);

}
