package com.example.usersapi.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(HttpStatus unauthorized, String message) {
        super(message);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
