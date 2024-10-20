package com.startup.bedok.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({AdvertisementNotExistsException.class})
    public ResponseEntity<Object> handleAdvertisementNotExistsException(
            AdvertisementNotExistsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UserWithSelectedPhoneAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithSelectedPhoneAlreadyExistsException(
            UserWithSelectedPhoneAlreadyExistsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UserWithSelectedEmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithSelectedEmailAlreadyExistsException(
            UserWithSelectedEmailAlreadyExistsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.METHOD_NOT_ALLOWED, String.format("%s header needed", ex.getHeaderName()));
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoFreeBedsException.class)
    public ResponseEntity<Object> handleNoFreeBedsException(NoFreeBedsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<Object> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PaymentLinkGenerationException.class)
    public ResponseEntity<Object> handlePaymentLinkGenerationException(PaymentLinkGenerationException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.BAD_GATEWAY, ex.getMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if(ex.getRootCause() instanceof SQLException sqlException){
            CustomException apiError =
                    new CustomException(HttpStatus.CONFLICT, sqlException.getMessage());
            return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
        }
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getCause().getMessage());
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
