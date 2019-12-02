package com.example.usersapi.service;

import com.example.usersapi.exception.UserRoleExistsException;
import com.example.usersapi.exception.InvalidArgumentException;
import com.example.usersapi.model.UserRole;

public interface UserRoleService {
    public UserRole createRole(UserRole newRole) throws UserRoleExistsException, InvalidArgumentException;
    public UserRole getRole(String roleName);
}
