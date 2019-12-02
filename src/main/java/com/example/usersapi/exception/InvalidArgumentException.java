package com.example.usersapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(HttpStatus unauthorized, String message) {
        super(message);
    }
}
