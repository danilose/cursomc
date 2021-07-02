package com.example.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.cursomc.services.exceptions.AuthorizationException;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.FileException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.BAD_REQUEST;
		ValidationError err = new ValidationError(
				code.value(), 
				"Erro de validação", 
				System.currentTimeMillis()
		);
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(code).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> dataIntegrity(FileException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> dataIntegrity(AmazonServiceException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> dataIntegrity(AmazonClientException e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> dataIntegrity(AmazonS3Exception e, HttpServletRequest request) {
		HttpStatus code = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis()
		);
		return ResponseEntity.status(code).body(err);
	}

}
