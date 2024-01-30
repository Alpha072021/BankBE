/**
 * 
 */
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * @author Dixith S N
 *
 */

@Entity
public class Document implements Comparable<Document>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "docId", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "docId", strategy = "com.alpha.bankApp.entity.idgenerator.DocumentIdGenerator")
	private String documentId;

	@Enumerated(EnumType.STRING)
	private DocumentType type;
	private LocalDateTime creationDateTime;

	private String documentImagePath;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	private DocsContainer docsContainer;

	/**
	 * @return the documentImagePath
	 */
	public String getDocumentImagePath() {
		return documentImagePath;
	}

	/**
	 * @param documentImagePath the documentImagePath to set
	 */
	public void setDocumentImagePath(String documentImagePath) {
		this.documentImagePath = documentImagePath;
	}

	/**
	 * @return the docsContainer
	 */
	public DocsContainer getDocsContainer() {
		return docsContainer;
	}

	/**
	 * @param docsContainer the docsContainer to set
	 */
	public void setDocsContainer(DocsContainer docsContainer) {
		this.docsContainer = docsContainer;
	}

	/**
	 * @return the creationDateTime
	 */
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	/**
	 * @param creationDateTime the creationDateTime to set
	 */
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the type
	 */
	public DocumentType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(DocumentType type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// constructors

	public Document() {

	}

	/**
	 * @param documentId
	 * @param image
	 * @param type
	 * @param status
	 */
	public Document(String documentId, DocumentType type, Status status) {
		super();
		this.documentId = documentId;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Document [documentId=" + documentId + ", type=" + type + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(documentId, status, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		return Objects.equals(documentId, other.documentId) && status == other.status && type == other.type;
	}

	@Override
	public int compareTo(Document o) {
		// TODO Auto-generated method stub
		return this.hashCode() - o.hashCode();
	}
}