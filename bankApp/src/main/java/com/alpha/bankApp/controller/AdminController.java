package com.alpha.bankApp.controller;

/*
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.CrossOrigin; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestHeader; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.alpha.bankApp.entity.Employee; import
 * com.alpha.bankApp.exception.VersionUnauthorizedException; import
 * com.alpha.bankApp.service.AdminService; import
 * com.alpha.bankApp.util.ResponseStructure;
 * 
 * //@CrossOrigin(origins = "https://192.168.0.189:3000/", allowedHeaders = {
 * "Origin", "Content-Type", "Accept", // "Authorization" }, methods = {
 * RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH,
 * RequestMethod.DELETE, // RequestMethod.PUT }, exposedHeaders = {
 * "Access-Control-Allow-Origin", // "Access-Control-Allow-Credentials" })
 * 
 * @RestController
 * 
 * @RequestMapping("api/version/{version}/admins") public class AdminController
 * {
 * 
 * @Autowired private AdminService adminService;
 * 
 * @PostMapping("/login") public ResponseEntity<ResponseStructure<String>>
 * adminLogin(@PathVariable String version,
 * 
 * @RequestParam String email, @RequestParam String password) {
 * 
 * if (version.equalsIgnoreCase("v1")) return
 * adminService.login(email.toLowerCase(), password); throw new
 * VersionUnauthorizedException("Not An Authorized Version"); }
 * 
 * // To Set Employee As Admin
 * 
 * @PatchMapping("/setAdmin") public ResponseEntity<ResponseStructure<String>>
 * setAdmin(@PathVariable String version,
 * 
 * @RequestParam String employeeId) { if (version.equalsIgnoreCase("v1")) return
 * adminService.setAdmin(employeeId); throw new
 * VersionUnauthorizedException("Not An Authorized Version"); }
 * 
 * 
 * // @DeleteMapping("/removeAdmin") // public
 * ResponseEntity<ResponseStructure<String>> removeAdmin(@RequestParam String
 * employeeId) { // return adminService.removeAdmin(employeeId); // }
 * 
 * @GetMapping("/getAdmin") public ResponseEntity<ResponseStructure<Employee>>
 * getAdmin(@PathVariable String version,
 * 
 * @RequestHeader("Authorization") String token) { System.err.println("Hi"); if
 * (version.equalsIgnoreCase("v1")) return adminService.getAdmin(token); throw
 * new VersionUnauthorizedException("Not An Authorized Version"); }
 * 
 * }
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.bankApp.entity.Employee;
import com.alpha.bankApp.exception.VersionUnauthorizedException;
import com.alpha.bankApp.service.AdminService;
import com.alpha.bankApp.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/version/{version}/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Operation(summary = "Login Admin", description = "Return Breare token on sucessfull login", tags = {
			"Admin Profile" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucess", content = @Content(schema = @Schema(implementation = ResponseStructure.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Not Authorised"),
			@ApiResponse(responseCode = "403", description = "Access Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not An Authorized Version") })
	@Hidden
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<String>> adminLogin(@PathVariable String version,
			@RequestParam String email, @RequestParam String password) {

		if (version.equalsIgnoreCase("v1"))
			return adminService.login(email.toLowerCase(), password);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}

	@Operation(summary = "Get Admin", description = "Get Admin Details based on the JWT token", tags = {
			"Admin Profile" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucess", content = @Content(schema = @Schema(implementation = ResponseStructure.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Not Authorised"),
			@ApiResponse(responseCode = "403", description = "Access Forbidden"),
			@ApiResponse(responseCode = "404", description = "Not An Authorized Version") })
	@GetMapping("/getAdmin")
	public ResponseEntity<ResponseStructure<Employee>> getAdmin(@PathVariable String version,
			@RequestHeader("Authorization") String token) {
		if (version.equalsIgnoreCase("v1"))
			return adminService.getAdmin(token);
		throw new VersionUnauthorizedException("Not An Authorized Version");
	}
}
