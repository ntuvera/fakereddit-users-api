package com.example.usersapi.exception;

public class UnauthorizedLoginException extends Exception {
    public UnauthorizedLoginException(String message) {
        super(message);
    }
}
