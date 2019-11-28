package com.awcsoftware.app;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
	final static Logger logger = Logger.getLogger(AppExceptionHandler.class);
	  @Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	      HttpHeaders headers, HttpStatus status, WebRequest request) {
		  BindingResult errors = ex.getBindingResult();
		   FieldError error = errors.getFieldError();
		   logger.debug("error message "  +error.getDefaultMessage());  
	    ErrorDetails errorDetails = new ErrorDetails(LocalDate.now(),
	    		ex.getBindingResult().toString(),ex.getBindingResult().getFieldError().getDefaultMessage());
	    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	  }
}
