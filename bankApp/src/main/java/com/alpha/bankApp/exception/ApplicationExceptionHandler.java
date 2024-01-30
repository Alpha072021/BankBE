package com.alpha.bankApp.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.alpha.bankApp.util.ResponseStructure;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(BankNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleBankNotFoundException(BankNotFoundException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Bank not found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BranchNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleBranchNotFoundException(BranchNotFoundException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Branch not found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleEmployeeNotFoundException(
			EmployeeNotFoundException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Employee not found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BankNotAssignedMDException.class)
	public ResponseEntity<ResponseStructure<String>> handleBankNotAssignedMDException(
			BankNotAssignedMDException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Employee not found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseStructure<String>> handleDataIntegrityViolationException(
			DataIntegrityViolationException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage("Duplicate entry");
		responseStructure.setData(exception.getRootCause().getMessage());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> ref = ex.getAllErrors();
		Map<String, String> errors = new LinkedHashMap<>();
		for (ObjectError error : ref) {
			String message = error.getDefaultMessage();
			String fieldName = ((FieldError) error).getField();
			errors.put(fieldName, message);
		}
		ResponseStructure<Map<String, String>> responseStructure = new ResponseStructure<Map<String, String>>();
		responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(HttpStatus.BAD_REQUEST.name());
		responseStructure.setData(errors);
		return new ResponseEntity<>(responseStructure, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BankNotAssignedBranchException.class)
	public ResponseEntity<ResponseStructure<String>> handleBankNotAssignedBranchException(
			BankNotAssignedBranchException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Branch not found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(VersionUnauthorizedException.class)
	public ResponseEntity<ResponseStructure<String>> handleVersionUnauthorizedException(
			VersionUnauthorizedException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Version Not Exist");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmployeeNotAssingedRoleException.class)
	public ResponseEntity<ResponseStructure<String>> handleEmployeeNotAssingedRoleException(
			EmployeeNotAssingedRoleException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("No ManagingDirectors Exist");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvaildCredentials.class)
	public ResponseEntity<ResponseStructure<String>> handleInvaildCredentials(InvaildCredentials exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Invalid Login Credentials");
		responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleUserNotFoundException(UserNotFoundException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Not Found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ResponseStructure<String>> handleUnauthorizedException(UnauthorizedException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Not Found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BranchNotAssignedException.class)
	public ResponseEntity<ResponseStructure<String>> handleBranchNotAssignedException(
			BranchNotAssignedException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("UnAssigned");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomersNotHaveAccount.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomersNotHaveAccount(CustomersNotHaveAccount exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Not Found");
		responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidBankNameException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidBankNameException(
			InvalidBankNameException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setData(exception.getMessage());
		responseStructure.setMessage("Invalid Bank Name");
		responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.BAD_REQUEST);
	}

}
