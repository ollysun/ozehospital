package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

  private final LocalDateTime todayDate = LocalDateTime.now();

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    final ExceptionResponse exceptionResponse = new ExceptionResponse(
            todayDate,ex.getMessage(), HttpStatus.NOT_FOUND, details);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {

    final List<String> errors = new ArrayList<>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    final ExceptionResponse exceptionResponse = new ExceptionResponse(
            todayDate, "Parameters Not Valid",HttpStatus.BAD_REQUEST,
            errors);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());
    final ExceptionResponse exceptionResponse = new ExceptionResponse(
            todayDate, "Something went wrong while trying to process your request",
            HttpStatus.INTERNAL_SERVER_ERROR,
            details);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HospitalException.class)
  public ResponseEntity<Object> handleException(HospitalException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    final ExceptionResponse exceptionResponse = new ExceptionResponse(
            todayDate, ex.getMessage(),HttpStatus.BAD_REQUEST,
            details);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}