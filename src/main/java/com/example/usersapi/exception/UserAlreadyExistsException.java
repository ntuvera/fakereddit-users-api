package com.example.usersapi.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(HttpStatus badRequest, String message) {
        super(message);
    }
}
