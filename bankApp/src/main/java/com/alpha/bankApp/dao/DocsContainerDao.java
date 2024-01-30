/**
 * 
 */
package com.alpha.bankApp.dao;

import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.entity.Document;

/**
 * @author Dixith S N
 *
 */
public interface DocsContainerDao {
	// Create
	DocsContainer createDocsContainer(DocsContainer container);

	DocsContainer createDocsContainerForUser(String userId);

	DocsContainer createDocsContainerForEmployee(String employeeId);

	// Update
	DocsContainer addDocumentforUser(String userID, Document document);

	DocsContainer addDocumentforEmployee(String employeeId, Document document);

	DocsContainer removeDocumentFromUser(String userId, String documentId);

	DocsContainer removeDocumentFromEmployee(String employeeId, String documentId);

	// Read

	// Delete
	void removeDocsContainer(String docsContainerId);
}
