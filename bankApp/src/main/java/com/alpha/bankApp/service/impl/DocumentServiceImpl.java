package com.alpha.bankApp.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alpha.bankApp.dao.DocsContainerDao;
import com.alpha.bankApp.dao.DocumentDao;
import com.alpha.bankApp.dao.EmployeeDao;
import com.alpha.bankApp.dao.UserDao;
import com.alpha.bankApp.entity.DocsContainer;
import com.alpha.bankApp.entity.Document;
import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.entity.User;
import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.enums.Status;
import com.alpha.bankApp.exception.EmployeeNotFoundException;
import com.alpha.bankApp.exception.UnauthorizedException;
import com.alpha.bankApp.exception.UserNotFoundException;
import com.alpha.bankApp.exception.UserProfileNotFound;
import com.alpha.bankApp.service.DocumentService;
import com.alpha.bankApp.util.ResponseStructure;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Value("${file.upload-dir}")
	private String uploadDir;
	@Autowired
	private DocumentDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private DocsContainerDao containerDao;

	@Override
	public ResponseEntity<ResponseStructure<String>> saveDocument(String id, MultipartFile[] files,
			List<DocumentType> type, String users) {
		if (users.equalsIgnoreCase("employee")) {
			Employee employee = employeeDao.getEmployeeById(id);
			if (employee != null) {
				if (employee.getDocsContainer() == null) {
					DocsContainer docsContainer = new DocsContainer();
					docsContainer.setCreatedDateTime(LocalDateTime.now());
					employee.setDocsContainer(docsContainer);
					employee = employeeDao.saveEmployee(employee);
				}
				employee.setDocsContainer(saveFile(files, type, id, employee.getDocsContainer()));
				employeeDao.saveEmployee(employee);
				ResponseStructure<String> structure = new ResponseStructure<>();
				structure.setStatusCode(HttpStatus.CREATED.value());
				structure.setData("Created");
				structure.setMessage("Created");
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.CREATED);
			}
			throw new EmployeeNotFoundException("Employee with the Given Id " + id + " Not Found");
		} else if (users.equalsIgnoreCase("customer")) {
			Optional<User> optional = userDao.findUserById(id);
			if (optional.isPresent()) {
				User user = optional.get();
				user.setDocsContainer(saveFile(files, type, id, user.getDocsContainer()));
				userDao.saveUser(user);
				ResponseStructure<String> structure = new ResponseStructure<>();
				structure.setStatusCode(HttpStatus.CREATED.value());
				structure.setData("Created");
				structure.setMessage("Created");
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.CREATED);
			}
			throw new UserNotFoundException("Customer With the Given Id" + id + " Not Found");
		}

		throw new UnauthorizedException("Provide Proper User Info");
	}

	public DocsContainer saveFile(MultipartFile[] files, List<DocumentType> type, String userId,
			DocsContainer container) {
		List<String> filePathInfo = new ArrayList<>();
		List<Document> listDocuments = container.getDocuments();
		if (listDocuments == null) {
			listDocuments = new ArrayList<>();
		}
		int types = 0;
		for (MultipartFile file : files) {
			String data = file.getOriginalFilename();
			String res = "";
			for (int index = 0; index < data.length(); index++) {
				if (data.charAt(index) == '.') {
					res = data.substring(index, data.length());
				}
			}
			String fileName = type.get(types) + "_" + res;
			String filePath = uploadDir + "/" + userId + File.separator + fileName;

			// Create the directory if it doesn't exist
			File directory = new File(uploadDir + "/" + userId);
			if (!directory.exists()) {
				directory.mkdirs(); // Creates the directory including any necessary but nonexistent parent
									// directories
			}

			try {
				File profile = new File(filePath);
				profile.setReadable(true, false);
				file.transferTo(profile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			Document doc = new Document();
			doc.setCreationDateTime(LocalDateTime.now());
			doc.setDocumentImagePath(filePath);
			doc.setType(type.get(types));
			doc.setDocsContainer(container);
			doc.setStatus(Status.ACTIVE);
			doc = dao.createDocument(doc);
			listDocuments.add(doc);
			filePathInfo.add(filePath);
			++types;
			container.increaseDocumentsCount();
		}
		container.setDocuments(listDocuments);
		return container;
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> saveProfile(String id, MultipartFile file, String users) {

		if (users.equalsIgnoreCase("employee")) {
			Employee employee = employeeDao.getEmployeeById(id);
			if (employee != null) {
				DocsContainer docsContainer = employee.getDocsContainer();
				if (docsContainer == null) {
					docsContainer = new DocsContainer();
					docsContainer.setCreatedDateTime(LocalDateTime.now());
					docsContainer.setDocuments(new ArrayList<>());
					docsContainer = containerDao.createDocsContainer(docsContainer);
				}

				MultipartFile[] files = { file };
				employee.setDocsContainer(saveFile(files, Arrays.asList(DocumentType.PROFILE), id, docsContainer));
				employeeDao.updateEmployee(employee.getEmployeeId(), employee);
				ResponseStructure<String> structure = new ResponseStructure<>();
				structure.setStatusCode(HttpStatus.CREATED.value());
				structure.setData(employee.getDocsContainer().getDocuments().stream()
						.filter(document -> document.getType().equals(DocumentType.PROFILE))
						.collect(Collectors.toList()).get(0).getDocumentImagePath());
				structure.setMessage("Created");
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.CREATED);
			}
			throw new EmployeeNotFoundException("Employee with the Given Id " + id + " Not Found");
		} else if (users.equalsIgnoreCase("customer")) {
			Optional<User> optional = userDao.findUserById(id);
			if (optional.isPresent()) {
				User coustomer = optional.get();
				DocsContainer docsContainer = coustomer.getDocsContainer();
				if (docsContainer == null) {
					docsContainer = new DocsContainer();
					docsContainer.setCreatedDateTime(LocalDateTime.now());
					docsContainer.setDocuments(new ArrayList<>());
					docsContainer = containerDao.createDocsContainer(docsContainer);
				}
				MultipartFile[] files = { file };
				coustomer.setDocsContainer(saveFile(files, Arrays.asList(DocumentType.PROFILE), id, docsContainer));
				userDao.saveUser(coustomer);
				ResponseStructure<String> structure = new ResponseStructure<>();
				structure.setStatusCode(HttpStatus.CREATED.value());
				structure.setData(coustomer.getDocsContainer().getDocuments().stream()
						.filter(document -> document.getType().equals(DocumentType.PROFILE))
						.collect(Collectors.toList()).get(0).getDocumentImagePath());
				structure.setMessage("Created");
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.CREATED);
			}
			throw new UserNotFoundException("Customer With the Given Id" + id + " Not Found");
		}

		throw new UnauthorizedException("Provide Proper User Info");
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> findUserProfile(String id, String users) {
		if (users.equalsIgnoreCase("employee")) {
			String profilePath = employeeDao.findEmployeeProfileById(id);
			ResponseStructure<String> structure = new ResponseStructure<>();
			if (profilePath != null) {
				structure.setData(profilePath);
				structure.setMessage("Found");
				structure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
			}
			throw new UserProfileNotFound("User Profile Not Found");

		} else if (users.equalsIgnoreCase("customer")) {
			String profilePath = userDao.findUserProfileById(id);
			ResponseStructure<String> structure = new ResponseStructure<>();
			if (profilePath != null) {
				structure.setData(profilePath);
				structure.setMessage("Found");
				structure.setStatusCode(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
			}
			throw new UserProfileNotFound("User Profile Not Found");
		}
		throw new UnauthorizedException("Provide Proper User Info");
	}

}
