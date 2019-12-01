package com.example.usersapi.service;

import com.example.usersapi.exception.NoMatchingUserFoundException;
import com.example.usersapi.exception.UserAlreadyExistsException;
import com.example.usersapi.exception.UserNotFoundException;
import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;

import java.util.Optional;

public interface UserService {
    public JwtResponse signUpUser(User newUser) throws UserAlreadyExistsException, UserNotFoundException;
    public JwtResponse loginUser(User user) throws UserNotFoundException;
    public Iterable<User> listAll();
    public Optional<User> findById(int userId) throws NoMatchingUserFoundException;
}
