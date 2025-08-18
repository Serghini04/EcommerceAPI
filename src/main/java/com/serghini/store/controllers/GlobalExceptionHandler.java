package com.serghini.store.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.serghini.store.dtos.ErrorDto;
import com.serghini.store.exceptions.CartEmptyException;
import com.serghini.store.exceptions.CartNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDto>	handleUnreadableMessage() {
		return ResponseEntity.badRequest().body(new ErrorDto("Invalid request body"));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
		var errors = new HashMap<String, String>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
	public ResponseEntity<?> handleCartException(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}