package com.eazylearn.controller;

import com.eazylearn.dto.response.ExceptionResponseDTO;
import com.eazylearn.exception.EntityAlreadyExistsException;
import com.eazylearn.exception.EntityDoesNotExistException;
import com.eazylearn.exception.JwtAuthenticationException;
import com.eazylearn.exception.UserAlreadyExistAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(EntityAlreadyExistsException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(EntityDoesNotExistException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(BadCredentialsException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(JwtAuthenticationException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(UserAlreadyExistAuthenticationException ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO(ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleException(Exception ex) {
        ExceptionResponseDTO exceptionResponse = new ExceptionResponseDTO("Sorry!Try again later");

        return new ResponseEntity<>(exceptionResponse, NOT_FOUND);
    }
}
