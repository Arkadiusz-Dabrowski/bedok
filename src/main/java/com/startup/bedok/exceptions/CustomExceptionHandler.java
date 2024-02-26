package com.startup.bedok.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AdvertisementNoExistsException.class})
    public ResponseEntity<Object> handleAdvertisementNotExistsException(
            AdvertisementNoExistsException ex) {
        return new ResponseEntity<Object>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserWithSelectedPhoneAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithSelectedPhoneAlreadyExistsException(
            UserWithSelectedPhoneAlreadyExistsException ex) {
        return new ResponseEntity<Object>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserWithSelectedEmailAlreadyExistsException.class})
    public ResponseEntity<Object> handleUserWithSelectedEmailAlreadyExistsException(
            UserWithSelectedEmailAlreadyExistsException ex) {
        return new ResponseEntity<Object>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
