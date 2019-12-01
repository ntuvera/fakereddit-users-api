package com.example.usersapi.exception;

import org.springframework.http.HttpStatus;

public class UserRoleExistsException extends Exception {
    public UserRoleExistsException(HttpStatus unauthorized, String message) {
        super(message);
    }
}
