package com.booking.brownfield.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.booking.brownfield.exception.RecordAlreadyPresentException;
import com.booking.brownfield.exception.RecordNotFoundException;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(RecordAlreadyPresentException.class)
	public ResponseEntity<?> RecordAlreadyPresentExceptionHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.FOUND);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> RecordNotFoundExceptionHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

}
