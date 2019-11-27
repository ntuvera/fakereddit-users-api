package com.example.usersapi.exception;

import org.springframework.http.HttpStatus;

public class NoMatchingUserFoundException extends Exception {
    public NoMatchingUserFoundException(HttpStatus notFound, String message) {
        super(message);
    }
}
