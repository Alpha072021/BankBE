/**
 * 
 */
package com.alpha.bankApp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * @author Dixith S N
 *
 */

@Entity
public class DocsContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "docsId", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name = "docsId", strategy = "com.alpha.bankApp.entity.idgenerator.DocsContainerIdGenerator")
	private String docsContainerId;

	private LocalDateTime createdDateTime;

	private LocalDateTime modifiedDateTime;

	@OneToMany(mappedBy = "docsContainer")
	private List<Document> documents;

	private int documentsCount;

	public void increaseDocumentsCount() {
		documentsCount++;
	}

	public void reduceDocumentsCount() {
		documentsCount--;
	}

	/**
	 * @return the docsContainerId
	 */
	public String getDocsContainerId() {
		return docsContainerId;
	}

	/**
	 * @param docsContainerId the docsContainerId to set
	 */
	public void setDocsContainerId(String docsContainerId) {
		this.docsContainerId = docsContainerId;
	}

	/**
	 * @return the createdDateTime
	 */
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * @return the modifiedDateTime
	 */
	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	/**
	 * @param modifiedDateTime the modifiedDateTime to set
	 */
	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	/**
	 * @return the documents
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	/**
	 * @return the noOfDocuments
	 */
	public int getDocumentsCount() {
		return documentsCount;
	}

	// constructor
	public DocsContainer() {

	}

	/**
	 * @param docsContainerId
	 * @param createdDateTime
	 * @param modifiedDateTime
	 */
	public DocsContainer(String docsContainerId, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
		super();
		this.docsContainerId = docsContainerId;
		this.createdDateTime = createdDateTime;
		this.modifiedDateTime = modifiedDateTime;
	}

	@Override
	public String toString() {
		return "DocsContainer [docsContainerId=" + docsContainerId + ", createdDateTime=" + createdDateTime
				+ ", modifiedDateTime=" + modifiedDateTime + ", documentsCount=" + documentsCount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdDateTime, docsContainerId, documents, documentsCount, modifiedDateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocsContainer other = (DocsContainer) obj;
		return Objects.equals(createdDateTime, other.createdDateTime)
				&& Objects.equals(docsContainerId, other.docsContainerId) && Objects.equals(documents, other.documents)
				&& documentsCount == other.documentsCount && Objects.equals(modifiedDateTime, other.modifiedDateTime);
	}
}