package com.alpha.bankApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alpha.bankApp.enums.DocumentType;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.DocumentService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/version/{version}/documents")
public class DocumentController {
	@Autowired
	private DocumentService service;

	@Operation(summary = "Save Document", description = "Return Location of document on sucessfull save")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucess", content = @Content(schema = @Schema(implementation = ResponseStructure.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Not Authorised"),
			@ApiResponse(responseCode = "403", description = "Access Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not An Authorized Version") })
	@PostMapping
	public ResponseEntity<ResponseStructure<String>> saveDocument(@PathVariable String version,
			@RequestParam("files") MultipartFile[] files, @RequestParam String id, @RequestParam String users,
			@RequestParam List<DocumentType> types) {
		if (version.equalsIgnoreCase("v1")) {
			return service.saveDocument(id, files, types, users);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@PostMapping("/saveProfile")
	public ResponseEntity<ResponseStructure<String>> saveProfile(@PathVariable String version,
			@RequestParam("files") MultipartFile files, @RequestParam String employeeId, @RequestParam String users) {
		if (version.equalsIgnoreCase("v1")) {
			return service.saveProfile(employeeId, files, users);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
	@GetMapping("/findProfile")
	public ResponseEntity<ResponseStructure<String>> findUserProfile(@PathVariable String version,
			@RequestParam String id, @RequestParam String users) {
		if (version.equalsIgnoreCase("v1")) {
			return service.findUserProfile(id, users);
		}
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
