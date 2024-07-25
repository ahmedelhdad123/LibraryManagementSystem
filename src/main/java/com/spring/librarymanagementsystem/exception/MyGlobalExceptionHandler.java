package com.spring.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiException> resourceNotFound(ResourceNotFound ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiException apiException=new ApiException(
                ex.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

}
