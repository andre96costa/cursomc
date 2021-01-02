package com.nelioalves.cursomc.resources.exceptions;

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
import com.nelioalves.cursomc.exceptions.AuthorizationException;
import com.nelioalves.cursomc.exceptions.DataIntregrityException;
import com.nelioalves.cursomc.exceptions.FileException;
import com.nelioalves.cursomc.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not found", e.getMessage() ,request.getRequestURI());  
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	} 
	
	@ExceptionHandler(DataIntregrityException.class)
	public ResponseEntity<StandartError> dataIntregrityException(DataIntregrityException e, HttpServletRequest request){
		StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Data Integrity", e.getMessage() ,request.getRequestURI());   
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", e.getMessage() ,request.getRequestURI());
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandartError> AuthorizationException(AuthorizationException e, HttpServletRequest request){
		StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Access not allowed", e.getMessage() ,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandartError> file(AuthorizationException e, HttpServletRequest request){
		StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "File error", e.getMessage() ,request.getRequestURI());  
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	} 
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandartError> amazonService(AmazonServiceException e, HttpServletRequest request){
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandartError err = new StandartError(System.currentTimeMillis(), code.value(), "Amazon service error", e.getMessage() ,request.getRequestURI());  
		return ResponseEntity.status(code).body(err);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandartError> amazonClient(AmazonClientException e, HttpServletRequest request){
		StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Amazon client error", e.getMessage() ,request.getRequestURI());  
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandartError> amazonS3(AmazonS3Exception e, HttpServletRequest request){
		StandartError err = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Amazon S3 error", e.getMessage() ,request.getRequestURI());  
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
