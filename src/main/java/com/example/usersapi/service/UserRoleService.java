package com.example.usersapi.service;

import com.example.usersapi.model.UserRole;

public interface UserRoleService {
    public UserRole createRole(UserRole newRole);
    public UserRole getRole(String roleName);
}
