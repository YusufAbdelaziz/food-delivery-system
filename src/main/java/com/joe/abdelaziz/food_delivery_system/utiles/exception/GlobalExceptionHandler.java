package com.joe.abdelaziz.food_delivery_system.utiles.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(DuplicateEntityException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateEntityException ex) {
    log.error(ex.getMessage(), ex);

    ErrorResponse body = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(body);
  }

  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFoundException(RecordNotFoundException ex) {

    log.error(ex.getMessage(), ex);
    ErrorResponse body = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(body);
  }

  @ExceptionHandler(EntityNotProvidedException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotProvided(RecordNotFoundException ex) {
    log.error(ex.getMessage(), ex);
    ErrorResponse body = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(body);
  }

  @Override
  @Nullable
  protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    log.error(ex.getMessage(), ex);
    List<String> errors = new ArrayList<>();
    var bindingResult = ex.getBindingResult();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {

      errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
    }

    for (ObjectError objectError : bindingResult.getGlobalErrors()) {
      errors.add(objectError.getDefaultMessage());
    }

    log.error(errors.toString(), ex);
    ErrorResponse errorResponse = new ErrorResponse(ex.toString(), errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    log.error(ex.getMessage(), ex);

    String message = "A database error occurred: " + ex.getMostSpecificCause().getMessage();
    ErrorResponse body = new ErrorResponse(message, Collections.singletonList(ex.getLocalizedMessage()));
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(body);
  }

  @Override
  @Nullable
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      @NonNull HttpRequestMethodNotSupportedException ex,
      @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    log.error(ex.getMessage(), ex);

    String message = "HTTP method not supported: " + ex.getMethod();
    ErrorResponse body = new ErrorResponse(message,
        Collections.singletonList(ex.getLocalizedMessage()));
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(body);
  }

  @Override
  @Nullable
  protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    Throwable cause = ex.getMostSpecificCause();
    log.error(ex.getMessage(), cause);

    if (cause instanceof InvalidFormatException) {
      InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
      if (invalidFormatException.getTargetType().isEnum()) {
        String enumType = invalidFormatException.getTargetType().getSimpleName();
        String message = "Invalid value for " + enumType + ": " + invalidFormatException.getValue();
        ErrorResponse body = new ErrorResponse(message, Collections.singletonList(
            "Accepted values: " + Arrays.toString(invalidFormatException.getTargetType().getEnumConstants())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(body);
      }
    }
    ErrorResponse body = new ErrorResponse("Malformed JSON request",
        Collections.singletonList(ex.getLocalizedMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(body);
  }

}
