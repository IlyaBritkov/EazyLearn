package com.eazylearn.exception_handling.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class EntityDoesNotExistException extends Exception {

    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
