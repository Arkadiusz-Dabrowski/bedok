package com.startup.bedok.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AdvertisementNoExistsException.class})
    public ResponseEntity<Object> handleAdvertisementNotExistsException(
            AdvertisementNoExistsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UserWithSelectedPhoneAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithSelectedPhoneAlreadyExistsException(
            UserWithSelectedPhoneAlreadyExistsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UserWithSelectedEmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithSelectedEmailAlreadyExistsException(
            UserWithSelectedEmailAlreadyExistsException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleException(MissingRequestHeaderException ex) {
        CustomException apiError =
                new CustomException(HttpStatus.METHOD_NOT_ALLOWED, String.format("%s header needed", ex.getHeaderName()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
