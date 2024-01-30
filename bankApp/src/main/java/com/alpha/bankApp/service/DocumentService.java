package com.alpha.bankApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.util.ResponseStructure;

public interface DocumentService {

	ResponseEntity<ResponseStructure<String>> saveDocument(String id, MultipartFile[] files, List<DocumentType> type,
			String users);

	// To Save Document into file and save filePath in database
	DocsContainer saveFile(MultipartFile[] files, List<DocumentType> type, String userId, DocsContainer container);

}
